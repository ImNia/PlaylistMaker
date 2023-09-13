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
                if(item.trim() != "") {
                    songs.add(converterSongs.map(
                        appDatabase.mediaDao().getSongPlaylist(item.toLong())
                    ))
                }
            }
        }
        emit(songs)
    }

    override suspend fun deleteSongPlaylist(song: SongItemPlaylist, idPlaylist: Long) {
        val playlist = appDatabase.mediaDao().getPlaylist(idPlaylist)
        appDatabase.mediaDao().changePlaylist(
            playlist.copy(
                songList = playlist.songList?.replace(song.trackId, "")?.trim(),
                countSong = playlist.countSong.dec()
            )
        )
        if(isNotExistsSongInPlaylists(song.trackId.trim())) {
            appDatabase.mediaDao().deleteSong(song.trackId)
        }
    }

    override suspend fun deleteSongs(songs: List<SongItemPlaylist>) {
        for (songItem in songs) {
            if(isNotExistsSongInPlaylists(songItem.trackId.trim())) {
                appDatabase.mediaDao().deleteSong(songItem.trackId.trim())
            }
        }
    }

    private suspend fun isNotExistsSongInPlaylists(songId: String): Boolean {
        val playlists = appDatabase.mediaDao().getPlaylists()
        var notExist = true
        for(item in playlists) {
            if(item.songList != null) {
                for(songItem in item.songList.split(" ")) {
                    if(songItem.trim() == songId) {
                        notExist = false
                    }
                }
            }
        }
        return notExist
    }
    override suspend fun deletePlaylist(id: Long) {
//        val songs = appDatabase.mediaDao().getPlaylist(id).songList?.split(" ")
        appDatabase.mediaDao().deletePlaylist(id)
    }
}