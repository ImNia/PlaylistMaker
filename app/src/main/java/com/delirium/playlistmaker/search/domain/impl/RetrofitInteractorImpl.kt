package com.delirium.playlistmaker.search.domain.impl

import android.util.Log
import com.delirium.playlistmaker.search.Resource
import com.delirium.playlistmaker.search.domain.api.RetrofitInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitRepository
import com.delirium.playlistmaker.search.domain.model.ResultRetrofitInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RetrofitInteractorImpl(
    private val repository: RetrofitRepository,
): RetrofitInteractor {
    override fun searchSongs(expression: String): Flow<ResultRetrofitInteractor> {
        return repository.searchSongs(expression).map { result ->
            when(result) {
                is Resource.Success -> {
                    Log.d("TEST", "${result.data?.first()}")
                    ResultRetrofitInteractor(
                        listSong = result.data,
                        error = null
                    )
                }
                is Resource.Error -> {
                    ResultRetrofitInteractor(
                        listSong = null,
                        error = result.message
                    )
                }
            }
        }
    }
}