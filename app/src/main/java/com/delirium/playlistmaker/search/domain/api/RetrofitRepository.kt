package com.delirium.playlistmaker.search.domain.api

import com.delirium.playlistmaker.search.Resource
import com.delirium.playlistmaker.search.data.models.SongItem

interface RetrofitRepository {
    fun searchSongs(expression: String): Resource<List<SongItem>>
}