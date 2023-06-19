package com.delirium.playlistmaker.search.domain.api

import com.delirium.playlistmaker.search.data.models.SongItem

interface RetrofitInteractor {

    fun searchSongs(expression: String, consumer: RetrofitConsumer)

    interface RetrofitConsumer {
        fun consume(foundSongs: List<SongItem>?, errorMessage: String?)
    }
}