package com.delirium.playlistmaker.player.domain.repository

import com.delirium.playlistmaker.player.domain.model.TrackModel
import kotlinx.coroutines.flow.Flow

interface SharingRepository {
    fun findSongInSharedPrefs(trackId: String): TrackModel?

    fun findSongInDB(trackId: String): Flow<TrackModel?>
}