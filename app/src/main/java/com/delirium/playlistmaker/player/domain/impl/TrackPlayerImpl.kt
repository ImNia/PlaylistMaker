package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.api.TrackPlayer

class TrackPlayerImpl: TrackPlayer {
    override fun play(trackId: String, statusObserver: TrackPlayer.StatusObserver) {
        TODO("Not yet implemented")
    }

    override fun pause(trackId: String) {
        TODO("Not yet implemented")
    }

    override fun seek(trackId: String, position: Float) {
        TODO("Not yet implemented")
    }

    override fun release(trackId: String) {
        TODO("Not yet implemented")
    }
}