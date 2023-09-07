package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.api.PlaylistInteractor
import com.delirium.playlistmaker.player.domain.repository.PlaylistRepository
import com.delirium.playlistmaker.player.ui.models.PlayListData
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val repository: PlaylistRepository
): PlaylistInteractor {
    override fun getPlaylists(): Flow<List<PlayListData>> {
        return repository.getPlaylists()
    }
}