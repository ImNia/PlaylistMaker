package com.delirium.playlistmaker.player.data.repository

import com.delirium.playlistmaker.player.data.converters.PlaylistDbConverters
import com.delirium.playlistmaker.player.domain.repository.PlaylistRepository
import com.delirium.playlistmaker.player.ui.models.PlayListData
import com.delirium.playlistmaker.utils.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: PlaylistDbConverters
): PlaylistRepository {
    override fun getPlaylists(): Flow<List<PlayListData>> = flow {
        appDatabase.playlistPlayerDao().getPlaylists().apply {
            val data: MutableList<PlayListData> = mutableListOf()
            this.forEach {
                data.add(converter.map(it))
            }
            emit(data)
        }
    }
}