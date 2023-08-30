package com.delirium.playlistmaker.search.domain.repository

import com.delirium.playlistmaker.search.domain.model.SongItem
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun cleanHistory()

    fun getHistoryDB(): Flow<List<SongItem>>
    suspend fun saveSongDB(song: SongItem)
}