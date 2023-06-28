package com.delirium.playlistmaker.search.domain.api

import com.delirium.playlistmaker.search.domain.model.SongItem

interface HistoryInteractor {
    fun saveSong(item: SongItem)
    fun getHistory(consumer: HistoryInteractor.HistoryConsumer)
    fun clearHistory()

    interface HistoryConsumer{
        fun consume(foundSongs: Array<SongItem>)
    }
}