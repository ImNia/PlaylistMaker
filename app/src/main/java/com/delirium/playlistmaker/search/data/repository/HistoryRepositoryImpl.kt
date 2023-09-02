package com.delirium.playlistmaker.search.data.repository

import com.delirium.playlistmaker.search.data.converters.SongDbConverters
import com.delirium.playlistmaker.utils.db.AppDatabase
import com.delirium.playlistmaker.utils.db.SongEntity
import com.delirium.playlistmaker.search.domain.repository.HistoryRepository
import com.delirium.playlistmaker.search.domain.model.SongItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val songDbConverter: SongDbConverters,
): HistoryRepository {
    override suspend fun cleanHistory() {
        appDatabase.songDao().deleteSongs()
    }

    override fun getHistoryDB(): Flow<List<SongItem>> = flow {
        val songs = appDatabase.songDao().getSongs()
        emit(convertFromSongEntity(songs))
    }

    private fun convertFromSongEntity(songs: List<SongEntity>): List<SongItem> {
        return formedHistory(songs).map { song -> songDbConverter.map(song) }
    }

    private fun formedHistory(songs: List<SongEntity>): List<SongEntity> {
        return songs.sortedByDescending { item ->
            item.saveData
        }.take(MAX_SIZE_HISTORY)
    }

    override suspend fun saveSongDB(song: SongItem) {
        val songEntities = findSongInDB(song.trackId) ?: songDbConverter.map(song)
        songEntities.saveData = (System.currentTimeMillis() / 1000).toString()
        appDatabase.songDao().insertSong(songEntities)
    }

    private suspend fun findSongInDB(trackId: String): SongEntity? {
        return appDatabase.songDao().getSong(trackId)
    }
    companion object {
        const val MAX_SIZE_HISTORY = 10
    }
}