package com.delirium.playlistmaker.media.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.media.domain.api.FavoriteInteractor
import com.delirium.playlistmaker.media.ui.fragment.favorite.FavoriteStateScreen
import kotlinx.coroutines.launch

class FavoriteTrackViewModel(
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {
    private var favoriteLiveData = MutableLiveData<FavoriteStateScreen>()
    fun getFavoriteLiveData(): MutableLiveData<FavoriteStateScreen> = favoriteLiveData
    fun initData() {
        viewModelScope.launch {
            favoriteInteractor.getFavoriteSongs().collect {
                favoriteLiveData.postValue(
                    FavoriteStateScreen.Content(
                        data = it
                    )
                )
            }
        }
    }
}