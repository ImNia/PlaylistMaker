package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.api.PlaylistInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.PlaylistRepository
import com.delirium.playlistmaker.player.domain.model.PlayListData
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val repository: PlaylistRepository
): PlaylistInteractor {
    override fun getPlaylists(): Flow<List<PlayListData>> {
        return repository.getPlaylists()
    }

    override suspend fun saveSong(song: TrackModel) {
        repository.saveSong(song)
    }

    override suspend fun addSongToPlaylist(playlist: PlayListData, song: TrackModel) {
        repository.addSongToPlaylist(playlist, song)
    }
}