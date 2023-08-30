package com.delirium.playlistmaker.search.domain.impl

import com.delirium.playlistmaker.search.domain.repository.HistoryRepository
import com.delirium.playlistmaker.search.domain.model.SongItem
import com.delirium.playlistmaker.search.domain.api.HistoryInteractor
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl(
    private val historyRepository: HistoryRepository
): HistoryInteractor {
    override fun clearHistory() {
        historyRepository.cleanHistory()
    }

    override fun getHistoryDB(): Flow<List<SongItem>> {
        return historyRepository.getHistoryDB()
    }

    override suspend fun saveSongDB(song: SongItem) {
        historyRepository.saveSongDB(song)
    }
}