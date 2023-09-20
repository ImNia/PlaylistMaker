package com.delirium.playlistmaker.search.domain.model

import com.google.gson.annotations.SerializedName

data class SongItem(
    val trackId: String,
    val trackName: String? = null,
    val artistName: String? = null,
    val collectionName: String? = null,
    val trackTimeMillis: Int? = null,
    @SerializedName("artworkUrl60")
    val artworkUrl60: String? = null,
    val artworkUrl100: String? = null,
    val releaseDate: String? = null,
    val country: String? = null,
    val primaryGenreName: String? = null,
    val previewUrl: String? = null,
    val isFavorite: Boolean = false,
    val saveData: String? = null,
    val addFavoriteDate: String? = null
) : ModelForAdapter
