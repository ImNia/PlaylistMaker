package com.delirium.playlistmaker.player.domain.repository

import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.ui.models.PlayStatus

interface MediaPlayerRepository {
    fun preparePlayer(track: TrackModel)
    fun getPlayStatus(): PlayStatus
    fun pausePlayer()
    fun startPlayer()
    fun closePlayer()
    fun getTimer(): PlayStatus
}