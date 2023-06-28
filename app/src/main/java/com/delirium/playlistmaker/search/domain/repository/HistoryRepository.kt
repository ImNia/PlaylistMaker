package com.delirium.playlistmaker.search.domain.repository

import com.delirium.playlistmaker.search.domain.model.SongItem

interface HistoryRepository {
    fun saveSong(song: SongItem)
    fun getHistory(): Array<SongItem>
    fun cleanHistory()
}