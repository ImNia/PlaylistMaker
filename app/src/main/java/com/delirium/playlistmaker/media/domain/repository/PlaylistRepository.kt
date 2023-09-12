package com.delirium.playlistmaker.media.domain.repository

import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.model.SongItemPlaylist
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getPlaylist(id: Long): Flow<PlayListData>
    fun getSongsPlaylist(idPlaylist: Long): Flow<List<SongItemPlaylist>>
    suspend fun deleteSongPlaylist(song: SongItemPlaylist, idPlaylist: Long)
}