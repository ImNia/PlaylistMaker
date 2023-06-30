package com.delirium.playlistmaker.player.domain.api

import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.ui.models.PlayStatus

interface PlayerInteractor {
    fun preparePlayer(track: TrackModel)
    fun getPlayerStatus(consumer: PlayerConsumer)
    fun pausePlayer()
    fun startPlayer()
    fun closePlayer()
    fun getTimerPlayer(consumer: PlayerConsumer)

    interface PlayerConsumer {
        fun onComplete(playerStatus: PlayStatus)
    }

}