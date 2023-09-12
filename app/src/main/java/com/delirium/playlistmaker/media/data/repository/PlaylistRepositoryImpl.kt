package com.delirium.playlistmaker.media.data.repository

import com.delirium.playlistmaker.media.data.converters.MediaDbConverters
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.repository.PlaylistRepository
import com.delirium.playlistmaker.utils.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: MediaDbConverters
): PlaylistRepository {
    override fun getPlaylist(id: Long): Flow<PlayListData> = flow {
        val playlist = appDatabase.mediaDao().getPlaylist(id)
        emit(converter.map(playlist))
    }
}