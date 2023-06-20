package com.delirium.playlistmaker.player.domain.api

import com.delirium.playlistmaker.player.data.TrackModel

interface TracksInteractor {
    fun prepareData(trackId: String, consumer: TracksConsumer)

    interface TracksConsumer{
        fun onComplete(trackModel: TrackModel?)
    }
}