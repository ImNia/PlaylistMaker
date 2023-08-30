package com.delirium.playlistmaker.player.domain.repository

import com.delirium.playlistmaker.player.domain.model.TrackModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun findSongInDB(trackId: String): Flow<TrackModel?>
    fun changeFavoriteState(trackId: String): Flow<TrackModel>
}