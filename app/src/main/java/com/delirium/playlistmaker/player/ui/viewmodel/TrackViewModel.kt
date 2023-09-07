package com.delirium.playlistmaker.player.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.player.domain.api.PlayerInteractor
import com.delirium.playlistmaker.player.domain.api.PlaylistInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.ui.models.PlayerState
import com.delirium.playlistmaker.player.ui.models.TrackScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TrackViewModel(
    private val trackId: String,
    private val tracksInteractor: TracksInteractor,
    private val playerInteractor: PlayerInteractor,
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private var screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData

    private val playerStateLiveData = MutableLiveData<PlayerState>()
    fun getPlayerStateLiveData(): LiveData<PlayerState> = playerStateLiveData

    private var track: TrackModel? = null
    private var timerJob: Job? = null

    fun initViewModel() {
        viewModelScope.launch {
            tracksInteractor.prepareData(trackId).collect { trackModel ->
                if (trackModel != null) {
                    screenStateLiveData.postValue(
                        TrackScreenState.Content(trackModel)
                    )
                    track = trackModel
                }
            }
        }
    }

    fun updateState() {
        viewModelScope.launch {
            playerInteractor.getState().collect {
                playerStateLiveData.value = it
            }
        }
    }

    fun preparePlayer() {
        track?.let {
            playerInteractor.preparePlayer(it)
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
                preparePlayer()
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
        viewModelScope.launch {
            playerInteractor.closePlayer().collect {
                playerStateLiveData.value = it
            }
        }
        timerJob?.cancel()
    }

    fun clickFavoriteButton() {
        track?.let {
            viewModelScope.launch {
                tracksInteractor.changeFavoriteState(it.trackId).collect {
                    screenStateLiveData.postValue(
                        TrackScreenState.Content(it)
                    )
                }
            }
        }
    }

    fun openBottomSheet() {
        viewModelScope.launch {
            playlistInteractor.getPlaylists().collect { result ->
                screenStateLiveData.postValue(
                    TrackScreenState.BottomSheetShow(
                        data = result
                    )
                )
            }
        }
    }
    companion object {
        private const val DELAY = 300L
    }
}