package com.delirium.playlistmaker.media.data.repository

import com.delirium.playlistmaker.media.data.converters.FavoriteSongDbConverters
import com.delirium.playlistmaker.media.domain.model.SongItemFavorite
import com.delirium.playlistmaker.media.domain.repository.FavoriteRepository
import com.delirium.playlistmaker.utils.db.AppDatabase
import com.delirium.playlistmaker.utils.db.SongEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: FavoriteSongDbConverters
): FavoriteRepository {
    override fun changeFavoriteState(song: SongItemFavorite): Flow<SongItemFavorite> = flow {
        appDatabase.favoriteDao().changeFavoriteState(
            converter.map(song)
        )
        emit(converter.map(appDatabase.favoriteDao().getSong(song.trackId)))
    }

    override fun getFavoriteSongs(): Flow<List<SongItemFavorite>> = flow {
        val songs = appDatabase.favoriteDao().getSongs()
        emit(convertFromSongEntity(songs))
    }

    private fun convertFromSongEntity(songs: List<SongEntity>): List<SongItemFavorite> {
        return formedFavorite(songs).map { song -> converter.map(song) }
    }

    private fun formedFavorite(songs: List<SongEntity>): List<SongEntity> {
        return songs.sortedByDescending { item ->
            item.saveData
        }
    }

}