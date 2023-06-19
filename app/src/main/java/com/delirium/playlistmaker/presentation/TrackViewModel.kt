package com.delirium.playlistmaker.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.delirium.playlistmaker.App
import com.delirium.playlistmaker.domain.api.TrackPlayer
import com.delirium.playlistmaker.domain.api.TracksInteractor
import com.delirium.playlistmaker.domain.models.TrackScreenState

class TrackViewModel(
    private val trackId: Int,
    private val tracksInteractor: TracksInteractor,
    private val trackPlayer: TrackPlayer,
) : ViewModel() {
    /*private var loadingLiveData = MutableLiveData(true)
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData
    private var screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
    private val playStatusLiveData = MutableLiveData<PlayStatus>()

    init {
        tracksInteractor.loadSomeData(
            trackId,
            onComplete = { trackModel ->
                screenStateLiveData.postValue(
                    TrackScreenState.Content(trackModel)
                )
            }
        )
    }

    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData

    fun play() {
        trackPlayer.play(
            trackId = trackId,
            // 1
            statusObserver = object : TrackPlayer.StatusObserver {
                override fun onProgress(progress: Float) {
                    // 2
                    playStatusLiveData.value = getCurrentPlayStatus().copy(progress = progress)
                }

                override fun onStop() {
                    // 3
                    playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = false)
                }

                override fun onPlay() {
                    // 4
                    playStatusLiveData.value = getCurrentPlayStatus().copy(isPlaying = true)
                }
            },
        )
    }

    // 5
    *//*fun pause() {
        trackPlayer.pause(trackId)
    }

    // 6
    override fun onCleared() {
        trackPlayer.release(trackId)
    }
    private fun getCurrentPlayStatus(): PlayStatus {
        return playStatusLiveData.value ?: PlayStatus(progress = 0f, isPlaying = false)
    }*//*

    private var loadingObserver: ((Boolean) -> Unit)? = null

    var isLoading: Boolean = true
        private set(value) {
            field = value
            loadingObserver?.invoke(value)
        }

    fun addLoadingObserver(loadingObserver: ((Boolean) -> Unit)) {
        this.loadingObserver = loadingObserver
    }

    fun removeLoadingObserver() {
        this.loadingObserver = null
    }

    companion object {
        fun getViewModelFactory(trackId: Int): ViewModelProvider.Factory = viewModelFactory {
            *//*initializer {
                val myApp = (this[APPLICATION_KEY] as App).provideTracksInteractor()
                val interactor = myApp.provideTracksInteractor()
                val trackPlayer = myApp.provideTrackPlayer()

                TrackViewModel(
                    trackId,
                    interactor,
                    trackPlayer
                )
            }*//*
        }
    }*/
}