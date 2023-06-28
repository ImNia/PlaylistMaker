package com.delirium.playlistmaker.search.domain.impl

import com.delirium.playlistmaker.search.Resource
import com.delirium.playlistmaker.search.domain.repository.NetworkClient
import com.delirium.playlistmaker.search.domain.model.SongItem
import com.delirium.playlistmaker.search.domain.model.SongsSearchRequest
import com.delirium.playlistmaker.search.domain.model.SongsSearchResponse
import com.delirium.playlistmaker.search.domain.api.RetrofitRepository

class RetrofitRepositoryImpl(
    private val networkClient: NetworkClient,
): RetrofitRepository {
    override fun searchSongs(expression: String): Resource<List<SongItem>> {
        val response = networkClient.getSongs(SongsSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }
            200 -> {
                Resource.Success(
                    (response as SongsSearchResponse).results.map {
                        SongItem(
                            trackId = it.trackId,
                            trackName = it.trackName,
                            artistName = it.artistName,
                            collectionName = it.collectionName,
                            trackTimeMillis = it.trackTimeMillis,
                            artworkUrl100 = it.artworkUrl100,
                            releaseDate = it.releaseDate,
                            country = it.country,
                            primaryGenreName = it.primaryGenreName,
                            previewUrl = it.previewUrl
                        )
                    }
                )
            }
            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}