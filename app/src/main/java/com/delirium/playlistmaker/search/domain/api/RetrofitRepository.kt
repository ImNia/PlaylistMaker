package com.delirium.playlistmaker.search.domain.api

import com.delirium.playlistmaker.search.Resource
import com.delirium.playlistmaker.search.domain.model.SongItem

interface RetrofitRepository {
    fun searchSongs(expression: String): Resource<List<SongItem>>
}