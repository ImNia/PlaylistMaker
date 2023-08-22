package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.api.PlayerInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.MediaPlayerRepository
import com.delirium.playlistmaker.player.ui.models.PlayerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlayerInteractorImpl(
    private val mediaPlayerRepository: MediaPlayerRepository
): PlayerInteractor {

    override fun preparePlayer(track: TrackModel) {
        mediaPlayerRepository.preparePlayer(track)
    }

    override fun isPlayerNotPrepared(): Flow<Boolean> = flow {
        emit(mediaPlayerRepository.isPlayerNotPrepared())
    }

    override fun pausePlayer(): Flow<PlayerState> = flow {
        mediaPlayerRepository.pausePlayer()
        emit(mediaPlayerRepository.playerState.value)
    }

    override fun startPlayer(): Flow<PlayerState> = flow {
        mediaPlayerRepository.startPlayer()
        emit(mediaPlayerRepository.playerState.value)
    }

    override fun closePlayer() {
        mediaPlayerRepository.closePlayer()
    }

    override fun getTimerPlayer(): Flow<PlayerState> = flow {
        mediaPlayerRepository.getTimer()
        emit(mediaPlayerRepository.playerState.value)
    }

}