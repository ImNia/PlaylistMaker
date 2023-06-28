package com.delirium.playlistmaker.search.ui.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.delirium.playlistmaker.search.domain.model.SongItem
import com.delirium.playlistmaker.search.domain.model.SongListItem
import com.delirium.playlistmaker.search.domain.api.HistoryInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitInteractor
import com.delirium.playlistmaker.search.ui.models.SearchState
import com.delirium.playlistmaker.settings.SingleLiveEvent

class SearchViewModel(
    private val retrofitInteractor: RetrofitInteractor,
    private val historyInteractor: HistoryInteractor,
) : ViewModel() {
    private var handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true

    private var openPlayerLiveData = SingleLiveEvent<String>()
    fun getOpenPlayerLiveData(): LiveData<String> = openPlayerLiveData

    private var searchStateLiveData = MutableLiveData<SearchState>()
    fun getSearchStateLiveData(): MutableLiveData<SearchState> = searchStateLiveData

    private var historyLiveData = MutableLiveData<Array<SongItem>>()
    fun getHistoryLiveData(): MutableLiveData<Array<SongItem>> = historyLiveData

    fun getSongOnInputText(expression: String) {
        searchStateLiveData.postValue(SearchState.Loading)
        handler.removeCallbacks(searchRunnable(expression))
        handler.postDelayed(searchRunnable(expression), SEARCH_DEBOUNCE_DELAY)
    }

    fun getHistory() {
        historyInteractor.getHistory(
            object : HistoryInteractor.HistoryConsumer {
                override fun consume(foundSongs: Array<SongItem>) {
                    historyLiveData.postValue(foundSongs)
                }
            }
        )
    }

    fun clearHistory() {
        historyInteractor.clearHistory()
        historyLiveData.postValue(arrayOf())
    }

    private fun searchRunnable(expression: String) =
        Runnable {
            retrofitInteractor.searchSongs(
                expression,
                object : RetrofitInteractor.RetrofitConsumer {
                    override fun consume(foundSongs: List<SongItem>?, errorMessage: String?) {
                        if (errorMessage != null) {
                            searchStateLiveData.postValue(
                                SearchState.Error
                            )
                        } else if (foundSongs?.isEmpty() == true) {
                            searchStateLiveData.postValue(
                                SearchState.Empty
                            )
                        } else {
                            searchStateLiveData.postValue(
                                SearchState.Content(
                                    SongListItem(
                                        songs = foundSongs!!
                                    )
                                )
                            )
                        }
                    }
                }
            )
        }

    fun openSongOnId(songItem: SongItem) {
        if (isClickDebounce()) {
            historyInteractor.saveSong(songItem)
            openPlayerLiveData.postValue(songItem.trackId)
        }
    }

    private fun isClickDebounce(): Boolean {
        val currentState = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed(
                { isClickAllowed = true }, CLICK_DEBOUNCE_DELAY
            )
        }
        return currentState
    }

    companion object {

        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }
}