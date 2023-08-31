package com.delirium.playlistmaker.player.data.repository

import com.delirium.playlistmaker.player.data.converters.SongPlayerDbConverters
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.DatabaseRepository
import com.delirium.playlistmaker.utils.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val songDbConverter: SongPlayerDbConverters,
) : DatabaseRepository {
    override fun findSongInDB(trackId: String): Flow<TrackModel?> = flow {
        val item = appDatabase.songPlayerDao().getSong(trackId)
        emit(
            songDbConverter.map(item).copy(
                artworkUrl100 = getCoverArtwork(item.artworkUrl100 ?: "")
            )
        )
    }

    override fun changeFavoriteState(trackId: String): Flow<TrackModel> = flow {
        var item = appDatabase.songPlayerDao().getSong(trackId)
        item = item.copy(isFavorite = if (item.isFavorite == 0) 1 else 0)
        item.addFavoriteDate = (System.currentTimeMillis() / 1000).toString()
        appDatabase.songPlayerDao().changeFavoriteState(item)
        emit(
            songDbConverter.map(appDatabase.songPlayerDao().getSong(trackId)).copy(
                artworkUrl100 = getCoverArtwork(item.artworkUrl100 ?: "")
            )
        )
    }

    private fun getCoverArtwork(artworkUrl: String) =
        artworkUrl.replaceAfterLast('/', "512x512bb.jpg")
}