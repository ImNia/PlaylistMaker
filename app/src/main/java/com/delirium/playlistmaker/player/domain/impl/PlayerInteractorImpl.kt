package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.api.PlayerInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.MediaPlayerRepository

class PlayerInteractorImpl(
    private val mediaPlayerRepository: MediaPlayerRepository
): PlayerInteractor {

    override fun preparePlayer(track: TrackModel) {
        mediaPlayerRepository.preparePlayer(track)
    }

    override fun pausePlayer(consumer: PlayerInteractor.PlayerConsumer) {
        val playerState = mediaPlayerRepository.pausePlayer()
        consumer.onComplete(playerState = playerState)
    }

    override fun startPlayer() {
        mediaPlayerRepository.startPlayer()
    }

    override fun closePlayer() {
        mediaPlayerRepository.closePlayer()
    }

    override fun getTimerPlayer(consumer: PlayerInteractor.PlayerConsumer) {
        val stateWithTimer = mediaPlayerRepository.getTimer()
        consumer.onComplete(playerState = stateWithTimer)
    }

}