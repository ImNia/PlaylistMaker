package com.delirium.playlistmaker.player.domain.repository

import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.ui.models.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface MediaPlayerRepository {
    val playerState: StateFlow<PlayerState>
    fun preparePlayer(track: TrackModel)
    fun pausePlayer(): String
    fun startPlayer()
    fun closePlayer()
    fun getTimer()
}