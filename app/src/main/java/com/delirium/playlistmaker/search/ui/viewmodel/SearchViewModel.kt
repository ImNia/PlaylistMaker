package com.delirium.playlistmaker.search.ui.viewmodel

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.delirium.playlistmaker.App
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.player.ui.activity.TrackActivity
import com.delirium.playlistmaker.search.data.models.ErrorItem
import com.delirium.playlistmaker.search.data.models.NotFoundItem
import com.delirium.playlistmaker.search.data.models.SongItem
import com.delirium.playlistmaker.search.data.models.SongListItem
import com.delirium.playlistmaker.search.domain.api.HistoryInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitInteractor
import com.delirium.playlistmaker.search.ui.models.SearchState
import com.delirium.playlistmaker.settings.SingleLiveEvent

class SearchViewModel(
    private val context: Context,
    private val retrofitInteractor: RetrofitInteractor,
    private val historyInteractor: HistoryInteractor,
) : ViewModel() {
    private var handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true

    private var songIntentLiveData = SingleLiveEvent<Intent>()
    fun getSongIntentLiveData(): LiveData<Intent> = songIntentLiveData

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
                                SearchState.Error(
                                    ErrorItem(
                                        res = R.drawable.not_connect_search,
                                        text = context.getString(R.string.not_connect_item_text),
                                        textSub = context.getString(R.string.not_connect_item_text_sub),
                                    )
                                )
                            )
                        } else if (foundSongs?.isEmpty() == true) {
                            searchStateLiveData.postValue(
                                SearchState.Empty(
                                    NotFoundItem(
                                        res = R.drawable.not_search,
                                        textProblem = context.getString(R.string.not_found),
                                    )
                                )
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
            val descSongIntent = Intent(context, TrackActivity::class.java)
            descSongIntent.putExtra(TRACK_ID, songItem.trackId)
            songIntentLiveData.postValue(descSongIntent)
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
        fun getViewModelFactory() = viewModelFactory {
            initializer {
                val myApp = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as App)
                val retrofit = myApp.providerRetrofitInteractor()
                val history = myApp.provideHistoryInteractor()
                SearchViewModel(myApp.applicationContext, retrofit, history)
            }
        }

        private const val SEARCH_DEBOUNCE_DELAY = 2000L
        private const val CLICK_DEBOUNCE_DELAY = 1000L

        private const val TRACK_ID = "TRACK_ID"
    }
}