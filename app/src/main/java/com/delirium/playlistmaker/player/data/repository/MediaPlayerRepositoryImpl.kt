package com.delirium.playlistmaker.player.data.repository

import android.media.MediaPlayer
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.MediaPlayerRepository
import com.delirium.playlistmaker.player.ui.models.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    override fun pausePlayer(): String {
        mediaPlayer.pause()
        _playerState.update {
            PlayerState.Paused(currentTimePlayer)
        }
        return currentTimePlayer
    }

    override fun startPlayer() {
        mediaPlayer.start()
        _playerState.update {
            PlayerState.Playing(currentTimePlayer)
        }
    }

    override fun closePlayer() {
        mediaPlayer.stop()
        mediaPlayer.reset()
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
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepare()

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.seekTo(0)
        }

        _playerState.update {
            PlayerState.Prepared()
        }
    }

    override fun isPlayerNotPrepared(): Boolean {
        return playerState.value is PlayerState.Default
    }
}