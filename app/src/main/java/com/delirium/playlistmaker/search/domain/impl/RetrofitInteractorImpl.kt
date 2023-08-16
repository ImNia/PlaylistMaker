package com.delirium.playlistmaker.search.domain.impl

import com.delirium.playlistmaker.search.Resource
import com.delirium.playlistmaker.search.domain.api.RetrofitInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitRepository
import com.delirium.playlistmaker.search.domain.model.SongItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RetrofitInteractorImpl(
    private val repository: RetrofitRepository,
): RetrofitInteractor {
    override fun searchSongs(expression: String): Flow<Pair<List<SongItem>?, String?>> {
        return repository.searchSongs(expression).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}