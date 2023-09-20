package com.delirium.playlistmaker.search.data.converters

import com.delirium.playlistmaker.utils.db.SongEntity
import com.delirium.playlistmaker.search.domain.model.SongItem

class SongDbConverters {
    fun map(song: SongItem): SongEntity {
        return SongEntity(
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
            isFavorite = if(song.isFavorite) 1 else 0,
            saveData = song.saveData,
        )
    }

    fun map(songEntity: SongEntity): SongItem {
        return SongItem(
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
            isFavorite = songEntity.isFavorite != 0,
            saveData = songEntity.saveData,
        )
    }
}