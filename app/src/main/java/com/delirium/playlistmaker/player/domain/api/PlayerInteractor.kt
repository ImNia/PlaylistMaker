package com.delirium.playlistmaker.player.domain.api

import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.ui.models.PlayerState
import kotlinx.coroutines.flow.Flow

interface PlayerInteractor {
    fun preparePlayer(track: TrackModel): Flow<PlayerState>
    fun pausePlayer(): Flow<PlayerState>
    fun startPlayer(): Flow<PlayerState>
    fun closePlayer()
    fun getTimerPlayer(): Flow<PlayerState>
}