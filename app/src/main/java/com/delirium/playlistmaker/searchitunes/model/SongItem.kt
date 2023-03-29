package com.delirium.playlistmaker.searchitunes.model

data class SongItem(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String
) : AdapterModel
