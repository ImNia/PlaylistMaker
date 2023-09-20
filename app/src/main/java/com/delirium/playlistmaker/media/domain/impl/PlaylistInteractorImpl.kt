package com.delirium.playlistmaker.media.domain.impl

import com.delirium.playlistmaker.media.domain.api.PlaylistInteractor
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.model.SongItemPlaylist
import com.delirium.playlistmaker.media.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val repository: PlaylistRepository
): PlaylistInteractor {
    override fun getPlaylist(id: Long): Flow<PlayListData> {
        return repository.getPlaylist(id)
    }

    override fun getSongsPlaylist(idPlaylist: Long): Flow<List<SongItemPlaylist>> {
        return repository.getSongsPlaylist(idPlaylist)
    }

    override suspend fun deleteSongPlaylist(song: SongItemPlaylist, idPlaylist: Long) {
        repository.deleteSongPlaylist(song, idPlaylist)
    }

    override suspend fun deletePlaylist(id: Long) {
        repository.deletePlaylist(id)
    }

    override suspend fun deleteSongs(songs: List<SongItemPlaylist>) {
        repository.deleteSongs(songs)
    }
}