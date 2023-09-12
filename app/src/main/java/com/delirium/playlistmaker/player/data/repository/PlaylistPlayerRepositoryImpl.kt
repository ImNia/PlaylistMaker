package com.delirium.playlistmaker.player.data.repository

import com.delirium.playlistmaker.player.data.converters.PlaylistDbConverters
import com.delirium.playlistmaker.player.data.converters.SongPlaylistDbConverters
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.PlaylistPlayerRepository
import com.delirium.playlistmaker.player.domain.model.PlayListData
import com.delirium.playlistmaker.utils.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistPlayerRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: PlaylistDbConverters,
    private val converterSongPlaylist: SongPlaylistDbConverters
): PlaylistPlayerRepository {
    override fun getPlaylists(): Flow<List<PlayListData>> = flow {
        appDatabase.playlistPlayerDao().getPlaylists().apply {
            val data: MutableList<PlayListData> = mutableListOf()
            this.forEach {
                data.add(converter.map(it))
            }
            emit(data)
        }
    }

    override suspend fun saveSong(song: TrackModel) {
        appDatabase.songPlaylistDao().insertSongForPlaylist(
            converterSongPlaylist.map(song)
        )
    }

    override suspend fun addSongToPlaylist(playlist: PlayListData, song: TrackModel) {
//        val data = appDatabase.playlistPlayerDao().getPlaylistById(playlist.id)
        appDatabase.playlistPlayerDao().changeSongsInPlaylist(
            converter.map(playlist)
        )
    }
}