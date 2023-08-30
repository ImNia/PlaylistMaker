package com.delirium.playlistmaker.player.data.converters

import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.utils.db.SongEntity

class SongPlayerDbConverters {
    fun map(song: TrackModel): SongEntity {
        return SongEntity(
            trackId = song.trackId,
            trackName = song.trackName,
            artistName = song.artistName,
            collectionName = song.collectionName,
            trackTimeMillis = song.trackTimeMillis,
            artworkUrl100 = song.artworkUrl100,
            releaseDate = song.releaseDate,
            country = song.country,
            primaryGenreName = song.primaryGenreName,
            previewUrl = song.previewUrl,
            isFavorite = if(song.isFavorite) 1 else 0
        )
    }

    fun map(songEntity: SongEntity): TrackModel {
        return TrackModel(
            trackId = songEntity.trackId,
            trackName = songEntity.trackName ?: "",
            artistName = songEntity.artistName ?: "",
            collectionName = songEntity.collectionName,
            trackTimeMillis = songEntity.trackTimeMillis ?: 0,
            artworkUrl100 = songEntity.artworkUrl100 ?: "",
            releaseDate = songEntity.releaseDate ?: "",
            country = songEntity.country ?: "",
            primaryGenreName = songEntity.primaryGenreName ?: "",
            previewUrl = songEntity.previewUrl ?: "",
            isFavorite = songEntity.isFavorite != 0
        )
    }
}