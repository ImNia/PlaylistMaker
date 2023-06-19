package com.delirium.playlistmaker.search.domain.impl

import com.delirium.playlistmaker.search.Resource
import com.delirium.playlistmaker.search.domain.api.RetrofitInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitRepository
import java.util.concurrent.Executors

class RetrofitInteractorImpl(
    private val repository: RetrofitRepository,
): RetrofitInteractor {
    private val executor = Executors.newCachedThreadPool()

    override fun searchSongs(expression: String, consumer: RetrofitInteractor.RetrofitConsumer) {
        executor.execute {
            when(val resource = repository.searchSongs(expression)) {
                is Resource.Success -> {
                    consumer.consume(resource.data, null)
                }
                is Resource.Error -> {
                    consumer.consume(null, resource.message)
                }
            }
        }
    }
}