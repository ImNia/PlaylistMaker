package com.delirium.playlistmaker.media.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.media.domain.api.FavoriteInteractor
import com.delirium.playlistmaker.media.domain.model.EmptyFavorite
import com.delirium.playlistmaker.media.domain.model.SongItemFavorite
import com.delirium.playlistmaker.media.ui.fragment.favorite.FavoriteStateScreen
import com.delirium.playlistmaker.settings.SingleLiveEvent
import com.delirium.playlistmaker.utils.debounceClick
import kotlinx.coroutines.launch

class FavoriteTrackViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private var openPlayerLiveData = SingleLiveEvent<String>()
    fun getOpenPlayerLiveData(): LiveData<String> = openPlayerLiveData

    private var favoriteLiveData = MutableLiveData<FavoriteStateScreen>()
    fun getFavoriteLiveData(): MutableLiveData<FavoriteStateScreen> = favoriteLiveData

    private val clickDebounce =
        debounceClick<SongItemFavorite>(CLICK_DEBOUNCE_DELAY, viewModelScope) { songItem ->
            handleClickOnSong(songItem)
        }

    fun initData() {
        viewModelScope.launch {
            favoriteInteractor.getFavoriteSongs().collect {
                if (it.isEmpty()) {
                    favoriteLiveData.postValue(
                        FavoriteStateScreen.Content(
                            data = listOf(EmptyFavorite())
                        )
                    )
                } else {
                    favoriteLiveData.postValue(
                        FavoriteStateScreen.Content(
                            data = it
                        )
                    )
                }
            }
        }
    }

    fun openSongById(songItem: SongItemFavorite) {
        handleClickOnSong(songItem)
    }
    private fun handleClickOnSong(songItem: SongItemFavorite) {
        openPlayerLiveData.postValue(songItem.trackId)
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}