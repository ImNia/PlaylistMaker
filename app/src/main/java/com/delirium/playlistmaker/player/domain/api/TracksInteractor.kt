package com.delirium.playlistmaker.player.domain.api

import com.delirium.playlistmaker.player.domain.model.TrackModel
import kotlinx.coroutines.flow.Flow

interface TracksInteractor {
    fun prepareData(trackId: String): Flow<TrackModel?>
}