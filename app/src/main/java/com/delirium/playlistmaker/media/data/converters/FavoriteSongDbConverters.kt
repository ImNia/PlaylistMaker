package com.delirium.playlistmaker.media.data.converters

import com.delirium.playlistmaker.media.domain.model.SongItemFavorite
import com.delirium.playlistmaker.utils.db.FavoriteSongEntity

class FavoriteSongDbConverters {
    fun map(song: SongItemFavorite): FavoriteSongEntity {
        return FavoriteSongEntity(
            trackId = song.trackId,
            trackName = song.trackName,
            artistName = song.artistName,
            collectionName = song.collectionName,
            trackTimeMillis = song.trackTimeMillis,
            artworkUrl60 = song.artworkUrl60,
            artworkUrl100 = song.artworkUrl100,
            releaseDate = song.releaseDate,
            country = song.country,
            primaryGenreName = song.primaryGenreName,
            previewUrl = song.previewUrl,
            isFavorite = if(song.isFavorite) 1 else 0
        )
    }

    fun map(songEntity: FavoriteSongEntity): SongItemFavorite {
        return SongItemFavorite(
            trackId = songEntity.trackId,
            trackName = songEntity.trackName ?: "",
            artistName = songEntity.artistName ?: "",
            collectionName = songEntity.collectionName,
            trackTimeMillis = songEntity.trackTimeMillis ?: 0,
            artworkUrl60 = songEntity.artworkUrl60 ?: "",
            artworkUrl100 = songEntity.artworkUrl100 ?: "",
            releaseDate = songEntity.releaseDate ?: "",
            country = songEntity.country ?: "",
            primaryGenreName = songEntity.primaryGenreName ?: "",
            previewUrl = songEntity.previewUrl ?: "",
            isFavorite = songEntity.isFavorite != 0
        )
    }
}