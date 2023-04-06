package com.delirium.playlistmaker.search.model

data class SongItem(
    val trackId: String,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String
) : AdapterModel
