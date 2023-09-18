package com.delirium.playlistmaker.player.data.converters

import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.utils.db.SongPlaylistEntity

class SongPlaylistPlayerDbConverters {
    fun map(song: SongPlaylistEntity): TrackModel {
        return TrackModel(
            trackId = song.trackId,
            trackName = song.trackName ?: "",
            artistName = song.artistName ?: "",
            collectionName = song.collectionName,
            trackTimeMillis = song.trackTimeMillis ?: 0,
            artworkUrl60 = song.artworkUrl60,
            artworkUrl100 = song.artworkUrl100 ?: "",
            releaseDate = song.releaseDate ?: "",
            country = song.country ?: "",
            primaryGenreName = song.primaryGenreName ?: "",
            previewUrl = song.previewUrl ?: "",
            isFavorite = song.isFavorite != 0,
        )
    }
    fun map(songEntity: TrackModel): SongPlaylistEntity {
        return SongPlaylistEntity(
            trackId = songEntity.trackId,
            trackName = songEntity.trackName,
            artistName = songEntity.artistName,
            collectionName = songEntity.collectionName,
            trackTimeMillis = songEntity.trackTimeMillis,
            artworkUrl60 = songEntity.artworkUrl60,
            artworkUrl100 = songEntity.artworkUrl100,
            releaseDate = songEntity.releaseDate,
            country = songEntity.country,
            primaryGenreName = songEntity.primaryGenreName,
            previewUrl = songEntity.previewUrl,
            isFavorite = if(songEntity.isFavorite) 1 else 0,
        )
    }
}