package com.delirium.playlistmaker.player.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.player.domain.api.PlayerInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.ui.models.PlayerState
import com.delirium.playlistmaker.player.ui.models.TrackScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

    private val playerStateLiveData = MutableLiveData<PlayerState>()
    fun getPlayerStateLiveData(): LiveData<PlayerState> = playerStateLiveData

    private var track: TrackModel? = null
    private var timerJob: Job? = null

    fun preparePlayer() {
        track?.let {
            playerInteractor.preparePlayer(it)

        }
        playerStateLiveData.postValue(PlayerState.Prepared())
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (playerStateLiveData.value?.isPlayButtonEnabled == true) {
                delay(DELAY)
                getCurrentPlayStatus()
            }
        }
    }

    fun clickButtonPlay() {
        if (playerStateLiveData.value is PlayerState.Playing) {
            pausePlayer()
        } else {
            startPlayer()
        }
    }
    private fun startPlayer() {
        playerInteractor.startPlayer()
        getCurrentPlayStatus()
        startTimer()

    }

    private fun pausePlayer() {
        playerInteractor.pausePlayer(
            object : PlayerInteractor.PlayerConsumer {
                override fun onComplete(playerState: String) {
                    playerStateLiveData.postValue(
                        PlayerState.Paused(
                            progress = playerState
                        )
                    )
                }
            }
        )
        timerJob?.cancel()
    }

    fun closeScreen() {
        playerInteractor.closePlayer()
        playerStateLiveData.postValue(PlayerState.Default())
    }

    private fun getCurrentPlayStatus() {
        playerInteractor.getTimerPlayer(
            object : PlayerInteractor.PlayerConsumer {
                override fun onComplete(playerState: String) {
                    playerStateLiveData.postValue(
                        PlayerState.Playing(
                            progress = playerState
                        )
                    )
                }
            }
        )
    }

    companion object {
        private const val DELAY = 300L
    }
}