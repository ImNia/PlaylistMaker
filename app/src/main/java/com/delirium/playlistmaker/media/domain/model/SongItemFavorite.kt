package com.delirium.playlistmaker.media.domain.model

data class SongItemFavorite(
    val trackId: String,
    val trackName: String? = null,
    val artistName: String? = null,
    val collectionName: String? = null,
    val trackTimeMillis: Int? = null,
    val artworkUrl100: String? = null,
    val releaseDate: String? = null,
    val country: String? = null,
    val primaryGenreName: String? = null,
    val previewUrl: String? = null,
    val isFavorite: Boolean = false
)