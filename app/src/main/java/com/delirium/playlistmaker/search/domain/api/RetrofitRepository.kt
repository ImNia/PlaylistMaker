package com.delirium.playlistmaker.search.domain.api

import com.delirium.playlistmaker.search.Resource
import com.delirium.playlistmaker.search.domain.model.SongItem
import kotlinx.coroutines.flow.Flow

interface RetrofitRepository {
    fun searchSongs(expression: String): Flow<Resource<List<SongItem>>>
}