package com.delirium.playlistmaker.search.itunes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ITunesSetting {
    private const val CONST_URL = "http://itunes.apple.com"
    val itunesInstant: ITunesServiceApi
        get() = getClient().create(ITunesServiceApi::class.java)

    private fun getClient(): Retrofit = Retrofit.Builder()
        .baseUrl(CONST_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
