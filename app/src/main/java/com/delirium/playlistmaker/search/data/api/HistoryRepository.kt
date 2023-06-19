package com.delirium.playlistmaker.search.data.api

import com.delirium.playlistmaker.search.data.models.SongItem

interface HistoryRepository {
    fun saveSong(song: SongItem)
    fun getHistory(): Array<SongItem>
    fun cleanHistory()
}