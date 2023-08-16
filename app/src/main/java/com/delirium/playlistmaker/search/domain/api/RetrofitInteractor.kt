package com.delirium.playlistmaker.search.domain.api

import com.delirium.playlistmaker.search.domain.model.SongItem
import kotlinx.coroutines.flow.Flow

interface RetrofitInteractor {
    fun searchSongs(expression: String): Flow<Pair<List<SongItem>?, String?>>
}