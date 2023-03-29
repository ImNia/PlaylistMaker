package com.delirium.playlistmaker.searchitunes

import com.delirium.playlistmaker.searchitunes.model.DataITunes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ITunesServiceApi {
    @GET("/search?entity=song")
    fun getSongs(
        @Query("term") text: String
    ): Call<DataITunes>
}