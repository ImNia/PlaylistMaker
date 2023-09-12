package com.delirium.playlistmaker.media.data.repository

import com.delirium.playlistmaker.media.data.converters.MediaDbConverters
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.repository.MediaCreateRepository
import com.delirium.playlistmaker.utils.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaCreateRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: MediaDbConverters
): MediaCreateRepository {
    override fun createMediaPlayer(playList: PlayListData): Flow<Boolean> = flow {
        appDatabase.mediaDao().createPlayList(converter.map(playList))
        emit(true)
    }

    override fun getPlayLists(): Flow<List<PlayListData>> = flow {
        appDatabase.mediaDao().getPlaylists().apply {
            val data: MutableList<PlayListData> = mutableListOf()
            this.forEach {
                data.add(converter.map(it))
            }
            emit(data)
        }
    }
}