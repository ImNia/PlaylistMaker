package com.delirium.playlistmaker.player.ui.viewmodel

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.delirium.playlistmaker.App
import com.delirium.playlistmaker.player.data.TrackModel
import com.delirium.playlistmaker.player.data.model.PlayerState
import com.delirium.playlistmaker.player.domain.api.TrackPlayer
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.ui.models.PlayStatus
import com.delirium.playlistmaker.player.ui.models.TrackScreenState
import java.text.SimpleDateFormat
import java.util.Locale

class TrackViewModel(
    private val trackId: String,
    private val tracksInteractor: TracksInteractor,
    private val trackPlayer: TrackPlayer,
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
                    } else {
                        //TODO error
                    }
                }
            }
        )
    }

    private var isPlaying: Boolean = false

    private var loadingLiveData = MutableLiveData(true)
    fun getLoadingLiveData(): LiveData<Boolean> = loadingLiveData
    private var screenStateLiveData = MutableLiveData<TrackScreenState>(TrackScreenState.Loading)
    fun getScreenStateLiveData(): LiveData<TrackScreenState> = screenStateLiveData

    private val playStatusLiveData = MutableLiveData<PlayStatus>()
    fun getPlayStatusLiveData(): LiveData<PlayStatus> = playStatusLiveData

    private val mediaPlayer = MediaPlayer()
    private var track: TrackModel? = null

    private var mainThreadHandler: Handler? = null
    private val runnable = updateTimer()

    fun preparePlayer() {
        mediaPlayer.setDataSource(track?.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playStatusLiveData.postValue(
                PlayStatus(
                    progress = "0",
                    isPlaying = false,
                    playerStatus = PlayerState.STATE_PREPARED,
                )
            )
        }
        mediaPlayer.setOnCompletionListener {
            playStatusLiveData.postValue(
                PlayStatus(
                    progress = "0",
                    isPlaying = false,
                    playerStatus = PlayerState.STATE_PREPARED,
                )
            )
            mediaPlayer.seekTo(0)
        }
        mainThreadHandler = Handler(Looper.getMainLooper())
    }

    fun play() {
        if (isPlaying) {
            mediaPlayer.pause()
            playStatusLiveData.value =
                getCurrentPlayStatus().copy(playerStatus = PlayerState.STATE_PAUSED)
            mainThreadHandler?.removeCallbacks(runnable)
        } else {
            mediaPlayer.start()
            playStatusLiveData.value =
                getCurrentPlayStatus().copy(playerStatus = PlayerState.STATE_PLAYING)
            mainThreadHandler?.post(runnable)
        }
        isPlaying = !isPlaying
    }

    private fun getCurrentPlayStatus(): PlayStatus {
        return playStatusLiveData.value ?: PlayStatus(
            progress = "0",
            isPlaying = false,
            PlayerState.STATE_DEFAULT
        )
    }

    private fun updateTimer(): Runnable {
        return object : Runnable {
            override fun run() {
                val currentTime = SimpleDateFormat(
                    "mm:ss",
                    Locale.getDefault()
                ).format(mediaPlayer.currentPosition)
                playStatusLiveData.value = getCurrentPlayStatus().copy(progress = currentTime)
                mainThreadHandler?.postDelayed(this, DELAY)
            }
        }
    }

    companion object {
        private const val DELAY = 1000L
        fun getViewModelFactory(trackId: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myApp = (this[APPLICATION_KEY] as App)
                val interactor = myApp.provideTracksInteractor()
                val trackPlayer = myApp.provideTrackPlayer()

                TrackViewModel(
                    trackId,
                    interactor,
                    trackPlayer
                )
            }
        }
    }
}