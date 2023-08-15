package com.delirium.playlistmaker.player.data.repository

import android.media.MediaPlayer
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.MediaPlayerRepository
import java.text.SimpleDateFormat
import java.util.Locale

class MediaPlayerRepositoryImpl(
    private val mediaPlayer: MediaPlayer,
) : MediaPlayerRepository {
    private var currentTimePlayer = "00:00"
    override fun pausePlayer(): String {
        mediaPlayer.pause()
        return getTimer()
    }

    override fun startPlayer() {
        mediaPlayer.start()
    }

    override fun closePlayer() {
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    override fun getTimer(): String {
        currentTimePlayer = SimpleDateFormat(
            "mm:ss",
            Locale.getDefault()
        ).format(mediaPlayer.currentPosition)
        return currentTimePlayer
    }

    override fun preparePlayer(track: TrackModel) {
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepareAsync()

        mediaPlayer.setOnCompletionListener {
//            playerState = PlayerState.Prepared()
            mediaPlayer.seekTo(0)
        }
    }
}