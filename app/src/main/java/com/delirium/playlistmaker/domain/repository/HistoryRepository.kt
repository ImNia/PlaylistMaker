package com.delirium.playlistmaker.domain.repository

import com.delirium.playlistmaker.domain.models.SongItem

interface HistoryRepository {
    fun saveSong(song: SongItem)
    fun getHistory(): Array<SongItem>
    fun cleanHistory()
}