package com.delirium.playlistmaker.search.model

data class SongItem(
    val trackId: String,
    val trackName: String,
    val artistName: String,
    val collectionName: String? = null,
    val trackTimeMillis: Int,
    val artworkUrl100: String,
    val releaseDate: String,
    val country: String,
    val primaryGenreName: String,
    val previewUrl: String,
) : AdapterModel
