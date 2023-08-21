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
            viewModelScope.launch {
                playerInteractor.preparePlayer(it).collect {
                    playerStateLiveData.value = it
                }
            }
        }
    }

    private fun startTimer() {
        var isNotPrepared = false
        viewModelScope.launch {
            playerInteractor.isPlayerNotPrepared().collect {
                isNotPrepared = it
            }
            if (isNotPrepared) {
                screenStateLiveData.postValue(
                    TrackScreenState.PlayerNotPrepared
                )
            } else {
                timerJob = launch {
                    playerInteractor.startPlayer().collect {
                        playerStateLiveData.value = it
                    }
                    while (playerStateLiveData.value?.isPlayButtonEnabled == true) {
                        delay(DELAY)
                        playerInteractor.getTimerPlayer().collect {
                            playerStateLiveData.value = it
                        }
                    }
                }

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
        startTimer()
    }

    fun pausePlayer() {
        viewModelScope.launch {
            playerInteractor.pausePlayer().collect {
                playerStateLiveData.value = it
            }
        }
        timerJob?.cancel()
    }

    fun closeScreen() {
        playerInteractor.closePlayer()
    }

    companion object {
        private const val DELAY = 300L
    }
}