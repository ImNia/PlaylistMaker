package com.delirium.playlistmaker.searchitunes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ITunesSetting {
    private const val CONST_URL = "https://itunes.apple.com"
    val itunesInstant: ITunesServiceApi
        get() = getClient().create(ITunesServiceApi::class.java)

    private fun getClient(): Retrofit = Retrofit.Builder()
        .baseUrl(CONST_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
