package com.delirium.playlistmaker.utils.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_song_table")
data class FavoriteSongEntity(
    @PrimaryKey
    val trackId: String,
    val trackName: String? = null,
    val artistName: String? = null,
    val collectionName: String? = null,
    val trackTimeMillis: Int? = null,
    val artworkUrl60: String? = null,
    val artworkUrl100: String? = null,
    val releaseDate: String? = null,
    val country: String? = null,
    val primaryGenreName: String? = null,
    val previewUrl: String? = null,
    var isFavorite: Int? = 0,
    var addFavoriteDate: String? = null
)
