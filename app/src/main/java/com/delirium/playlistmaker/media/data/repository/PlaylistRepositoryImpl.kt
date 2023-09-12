package com.delirium.playlistmaker.media.data.repository

import com.delirium.playlistmaker.media.data.converters.MediaDbConverters
import com.delirium.playlistmaker.media.data.converters.SongPlaylistDbConverters
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.model.SongItemPlaylist
import com.delirium.playlistmaker.media.domain.repository.PlaylistRepository
import com.delirium.playlistmaker.utils.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val converter: MediaDbConverters,
    private val converterSongs: SongPlaylistDbConverters
): PlaylistRepository {
    override fun getPlaylist(id: Long): Flow<PlayListData> = flow {
        val playlist = appDatabase.mediaDao().getPlaylist(id)
        emit(converter.map(playlist))
    }

    override fun getSongsPlaylist(idPlaylist: Long): Flow<List<SongItemPlaylist>> = flow {
        val playlist = appDatabase.mediaDao().getPlaylist(idPlaylist)
        val songs = mutableListOf<SongItemPlaylist>()
        if(playlist.songList != null) {
            for (item in playlist.songList.split(" ")) {
                songs.add(converterSongs.map(
                    appDatabase.mediaDao().getSongPlaylist(item.toLong())
                ))
            }
        }
        emit(songs)
    }
}