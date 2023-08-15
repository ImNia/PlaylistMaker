package com.delirium.playlistmaker.player.domain.repository

import com.delirium.playlistmaker.player.domain.model.TrackModel

interface MediaPlayerRepository {
    fun preparePlayer(track: TrackModel)
    fun pausePlayer(): String
    fun startPlayer()
    fun closePlayer()
    fun getTimer(): String
}