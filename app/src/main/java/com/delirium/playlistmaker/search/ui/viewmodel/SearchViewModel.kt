package com.delirium.playlistmaker.search.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.search.domain.model.SongItem
import com.delirium.playlistmaker.search.domain.model.SongListItem
import com.delirium.playlistmaker.search.domain.api.HistoryInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitInteractor
import com.delirium.playlistmaker.search.ui.models.SearchState
import com.delirium.playlistmaker.settings.SingleLiveEvent
import com.delirium.playlistmaker.utils.debounce
import com.delirium.playlistmaker.utils.debounceClick
import kotlinx.coroutines.launch

class SearchViewModel(
    private val retrofitInteractor: RetrofitInteractor,
    private val historyInteractor: HistoryInteractor,
) : ViewModel() {
    private var openPlayerLiveData = SingleLiveEvent<String>()
    fun getOpenPlayerLiveData(): LiveData<String> = openPlayerLiveData

    private var searchStateLiveData = MutableLiveData<SearchState>()
    fun getSearchStateLiveData(): MutableLiveData<SearchState> = searchStateLiveData

    private var latestSearchText: String? = null
    private var isSearching: Boolean = false
    private val searchDebounce =
        debounce<String>(SEARCH_DEBOUNCE_DELAY, viewModelScope, true) { changeText ->
            search(changeText)
        }

    private val clickDebounce =
        debounceClick<SongItem>(CLICK_DEBOUNCE_DELAY, viewModelScope) { songItem ->
            handleClickOnSong(songItem)
        }
    fun updateInputText(expression: String) {
        if (expression.isNotEmpty()) {
            searchStateLiveData.postValue(SearchState.Loading)

            if (latestSearchText != expression) {
                latestSearchText = expression
                searchDebounce(expression)
            }
        } else {
            historyInteractor.getHistory(
                object : HistoryInteractor.HistoryConsumer {
                    override fun consume(foundSongs: Array<SongItem>) {
                        searchStateLiveData.postValue(
                            SearchState.History(
                                data = foundSongs.asList()
                            )
                        )
                    }
                }
            )
        }
    }

    fun clearHistory() {
        historyInteractor.clearHistory()
        searchStateLiveData.postValue(
            SearchState.History(
                data = emptyList()
            )
        )
    }

    private fun search(expression: String) {
        viewModelScope.launch {
            retrofitInteractor.searchSongs(expression)
                .collect { pair ->
                    if (pair.second != null) {
                        searchStateLiveData.postValue(
                            SearchState.Error
                        )
                    } else if (pair.first?.isEmpty() == true) {
                        searchStateLiveData.postValue(
                            SearchState.Empty
                        )
                    } else {
                        searchStateLiveData.postValue(
                            SearchState.Content(
                                SongListItem(
                                    songs = pair.first!!
                                )
                            )
                        )
                    }
                }
        }
        isSearching = false
    }

    fun openSongOnId(songItem: SongItem) {
        clickDebounce(songItem)
    }

    private fun handleClickOnSong(songItem: SongItem) {
        historyInteractor.saveSong(songItem)
        openPlayerLiveData.postValue(songItem.trackId)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}