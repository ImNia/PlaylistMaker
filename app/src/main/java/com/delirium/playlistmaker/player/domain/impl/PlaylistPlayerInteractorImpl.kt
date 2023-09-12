package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.api.PlaylistPlayerInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.PlaylistPlayerRepository
import com.delirium.playlistmaker.player.domain.model.PlayListData
import kotlinx.coroutines.flow.Flow

class PlaylistPlayerInteractorImpl(
    private val repository: PlaylistPlayerRepository
): PlaylistPlayerInteractor {
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