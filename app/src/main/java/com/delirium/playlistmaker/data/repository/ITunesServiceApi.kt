package com.delirium.playlistmaker.data.repository

import com.delirium.playlistmaker.domain.models.DataITunes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesServiceApi {
    @GET("/search?entity=song")
    fun getSongs(
        @Query("term") text: String
    ): Call<DataITunes>
}