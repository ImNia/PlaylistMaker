package com.delirium.playlistmaker.search.data.network

import com.delirium.playlistmaker.search.domain.model.SongsSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesServiceApi {
    @GET("/search?entity=song")
    fun getSongs(
        @Query("term") text: String
    ): Call<SongsSearchResponse>
}