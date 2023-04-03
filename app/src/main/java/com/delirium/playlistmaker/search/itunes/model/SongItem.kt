package com.delirium.playlistmaker.search.itunes.model

data class SongItem(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String
) : AdapterModel
