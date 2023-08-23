package com.delirium.playlistmaker.player.data.repository

import android.media.MediaPlayer
import android.util.Log
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.MediaPlayerRepository
import com.delirium.playlistmaker.player.ui.models.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Locale

class MediaPlayerRepositoryImpl(
    private val mediaPlayer: MediaPlayer,
) : MediaPlayerRepository {
    private val currentTimePlayer
        get() = SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(mediaPlayer.currentPosition)

    private val _playerState = MutableStateFlow<PlayerState>(PlayerState.Default())
    override val playerState = _playerState.asStateFlow()
    override fun pausePlayer() {
        if(isPlayerActive()){
            mediaPlayer.pause()
            _playerState.update {
                PlayerState.Paused(currentTimePlayer)
            }
        }
    }

    override fun startPlayer() {
        if (!isPlayerNotPrepared()){
            mediaPlayer.start()
            _playerState.update {
                PlayerState.Playing(currentTimePlayer)
            }
        }
    }

    override fun closePlayer() {
        if(isPlayerActive()) mediaPlayer.stop()
        mediaPlayer.reset()
        _playerState.update {
            PlayerState.Default()
        }
    }

    override fun getTimer() {
        if (mediaPlayer.isPlaying) {
            _playerState.update {
                PlayerState.Playing(currentTimePlayer)
            }
        } else {
            _playerState.update {
                PlayerState.Prepared()
            }
        }
    }
    override fun preparePlayer(track: TrackModel) {
        try {
            mediaPlayer.setDataSource(track.previewUrl)
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                _playerState.update {
                    PlayerState.Prepared()
                }
            }

            mediaPlayer.setOnCompletionListener {
                if (isPlayerActive())
                    mediaPlayer.seekTo(0)
            }
        } catch (ex: Exception) {
            Log.d("MEDIA_PLAYER", "${ex.message}")
            _playerState.update {
                PlayerState.Error()
            }
        }
    }

    override fun isPlayerNotPrepared(): Boolean {
        return _playerState.value is PlayerState.Default
                || _playerState.value is PlayerState.Error
    }

    private fun isPlayerActive(): Boolean {
        return _playerState.value is PlayerState.Playing
                || _playerState.value is PlayerState.Paused
    }
}