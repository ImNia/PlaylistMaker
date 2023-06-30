package com.delirium.playlistmaker.player.ui.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.delirium.playlistmaker.player.domain.api.PlayerInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.ui.models.PlayStatus
import com.delirium.playlistmaker.player.ui.models.TrackScreenState

class TrackViewModel(
    private val trackId: String,
    private val tracksInteractor: TracksInteractor,
    private val playerInteractor: PlayerInteractor,
) : ViewModel() {
    init {
        tracksInteractor.prepareData(
            trackId,
            object : TracksInteractor.TracksConsumer {
                override fun onComplete(trackModel: TrackModel?) {
                    if (trackModel != null) {
                        screenStateLiveData.postValue(
                            TrackScreenState.Content(trackModel)
                        )
                        track = trackModel
                    }
                }
            }
        )
    }

    private var screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData

    private val playStatusLiveData = MutableLiveData<PlayStatus>()
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData

    private var track: TrackModel? = null

    private var mainThreadHandler: Handler? = null
    private val runnable = updateTimer()

    fun preparePlayer() {
        track?.let {
            playerInteractor.preparePlayer(it)
        }
        getCurrentPlayStatus()
        mainThreadHandler = Handler(Looper.getMainLooper())
    }

    fun play() {
        getCurrentPlayStatus()
        if (playStatusLiveData.value?.isPlaying == true) {
            playerInteractor.pausePlayer()
            getCurrentPlayStatus()
            mainThreadHandler?.removeCallbacks(runnable)
        } else {
            playerInteractor.startPlayer()
            getCurrentPlayStatus()
            mainThreadHandler?.post(runnable)
        }
    }

    fun closeScreen() {
        playerInteractor.closePlayer()
        mainThreadHandler?.removeCallbacks(runnable)
    }

    private fun getCurrentPlayStatus() {
        playerInteractor.getPlayerStatus(
            object : PlayerInteractor.PlayerConsumer {
                override fun onComplete(playerStatus: PlayStatus) {
                    playStatusLiveData.value = playerStatus
                }
            }
        )
    }

    private fun updateTimer(): Runnable {
        return object : Runnable {
            override fun run() {
                playerInteractor.getTimerPlayer(
                    object : PlayerInteractor.PlayerConsumer {
                        override fun onComplete(playerStatus: PlayStatus) {
                            playStatusLiveData.value = playerStatus
                        }
                    }
                )
                mainThreadHandler?.postDelayed(this, DELAY)
            }
        }
    }

    companion object {
        private const val DELAY = 1000L
    }
}