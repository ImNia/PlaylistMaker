package com.delirium.playlistmaker.search.domain.api

import com.delirium.playlistmaker.search.domain.model.SongItem
import kotlinx.coroutines.flow.Flow

interface HistoryInteractor {
    fun clearHistory()

    fun getHistoryDB(): Flow<List<SongItem>>
    suspend fun saveSongDB(song: SongItem)
}