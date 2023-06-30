package com.delirium.playlistmaker.player.data.repository

import android.media.MediaPlayer
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.MediaPlayerRepository
import com.delirium.playlistmaker.player.ui.models.PlayStatus
import com.delirium.playlistmaker.player.ui.models.PlayerState
import java.text.SimpleDateFormat
import java.util.Locale

class MediaPlayerRepositoryImpl(
    private val mediaPlayer: MediaPlayer,
) : MediaPlayerRepository {
    private var currentTimePlayer = "00:00"
    private var playStatus = PlayStatus(
        progress = currentTimePlayer,
        isPlaying = false,
        playerStatus = PlayerState.STATE_DEFAULT
    )

    override fun getPlayStatus() = playStatus
    override fun pausePlayer() {
        mediaPlayer.pause()
        playStatus = getPlayStatus().copy(
            isPlaying = false,
            playerStatus = PlayerState.STATE_PAUSED
        )
    }

    override fun startPlayer() {
        mediaPlayer.start()
        playStatus = getPlayStatus().copy(
            isPlaying = true,
            playerStatus = PlayerState.STATE_PLAYING
        )
    }

    override fun closePlayer() {
        mediaPlayer.reset()
    }

    override fun getTimer(): PlayStatus {
        currentTimePlayer = SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(mediaPlayer.currentPosition)
        playStatus = getPlayStatus().copy(progress = currentTimePlayer)
        return playStatus
    }

    override fun preparePlayer(track: TrackModel) {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playStatus = PlayStatus(
                progress = currentTimePlayer,
                isPlaying = false,
                playerStatus = PlayerState.STATE_PREPARED,
            )
        }
        mediaPlayer.setOnCompletionListener {
            playStatus = PlayStatus(
                progress = currentTimePlayer,
                isPlaying = false,
                playerStatus = PlayerState.STATE_PREPARED,
            )
            mediaPlayer.seekTo(0)
        }
        playStatus = playStatus.copy(
            playerStatus = PlayerState.STATE_PREPARED
        )
    }
}