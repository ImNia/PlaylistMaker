package com.delirium.playlistmaker.player.domain.api

import com.delirium.playlistmaker.player.domain.model.TrackModel

interface PlayerInteractor {
    fun preparePlayer(track: TrackModel)
    fun pausePlayer(consumer: PlayerConsumer)
    fun startPlayer()
    fun closePlayer()
    fun getTimerPlayer(consumer: PlayerConsumer)

    interface PlayerConsumer {
        fun onComplete(playerState: String)
    }

}