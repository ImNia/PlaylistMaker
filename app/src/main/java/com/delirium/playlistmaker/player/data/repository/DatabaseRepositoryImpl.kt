package com.delirium.playlistmaker.player.data.repository

import android.util.Log
import com.delirium.playlistmaker.player.data.converters.SongPlayerDbConverters
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.DatabaseRepository
import com.delirium.playlistmaker.utils.db.AppDatabase
import com.delirium.playlistmaker.utils.db.FavoriteSongEntity
import com.delirium.playlistmaker.utils.db.SongEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val songDbConverter: SongPlayerDbConverters,
) : DatabaseRepository {
    override fun findSongInDB(trackId: String): Flow<TrackModel?> = flow {
        val item = appDatabase.songPlayerDao().getSong(trackId) ?: appDatabase.songPlayerDao().getFavoriteSong(trackId)
        if (item is SongEntity) {
            emit(
                songDbConverter.map(item).copy(
                    artworkUrl100 = getCoverArtwork(item.artworkUrl100 ?: "")
                )
            )
        } else if (item is FavoriteSongEntity) {
            emit(
                songDbConverter.mapFavoriteToModel(item).copy(
                    artworkUrl100 = getCoverArtwork(item.artworkUrl100 ?: "")
                )
            )
        } else {
            emit(null)
        }
    }

    override fun changeFavoriteState(trackId: String): Flow<TrackModel> = flow {
        var item = appDatabase.songPlayerDao().getFavoriteSong(trackId)
        if(item != null) {
            item = item.copy(
                isFavorite = if (item.isFavorite == 0) 1 else 0,
                addFavoriteDate = (System.currentTimeMillis() / 1000).toString()
            )
            appDatabase.songPlayerDao().changeFavoriteState(item)
        } else {
            val newItem = appDatabase.songPlayerDao().getSong(trackId)
            if (newItem != null) {
                songDbConverter.mapFavorite(newItem).apply {
                    this.isFavorite = 1
                    this.addFavoriteDate = (System.currentTimeMillis() / 1000).toString()
                    appDatabase.songPlayerDao().insertFavoriteSong(this)
                }
            } else {
                Log.d("TEST", "PROBLEM")
            }
        }
        appDatabase.songPlayerDao().getSong(trackId)?.let {
            it.isFavorite = if (it.isFavorite == 0) 1 else 0
            appDatabase.songPlayerDao().changeFavoriteStateHistory(it)
        }

        val song = appDatabase.songPlayerDao().getSong(trackId)
        song?.let {
            emit(
                songDbConverter.map(it).copy(
                    artworkUrl100 = getCoverArtwork(it.artworkUrl100 ?: "")
                )
            )
        }
    }

    private fun getCoverArtwork(artworkUrl: String) =
        artworkUrl.replaceAfterLast('/', "512x512bb.jpg")
}