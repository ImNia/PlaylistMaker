package com.delirium.playlistmaker.search.domain.impl

import com.delirium.playlistmaker.search.domain.repository.HistoryRepository
import com.delirium.playlistmaker.search.domain.model.SongItem
import com.delirium.playlistmaker.search.domain.api.HistoryInteractor
import java.util.concurrent.Executors

class HistoryInteractorImpl(
    private val historyRepository: HistoryRepository
): HistoryInteractor {
    private val executor = Executors.newCachedThreadPool()

    override fun saveSong(item: SongItem) {
        historyRepository.saveSong(item)
    }

    override fun getHistory(consumer: HistoryInteractor.HistoryConsumer) {
        executor.execute {
            consumer.consume(historyRepository.getHistory())
        }
    }

    override fun clearHistory() {
        historyRepository.cleanHistory()
    }
}