package com.delirium.playlistmaker.media.domain.impl

import com.delirium.playlistmaker.media.domain.api.PlaylistInteractor
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.repository.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val repository: PlaylistRepository
): PlaylistInteractor {
    override fun getPlaylist(id: Long): Flow<PlayListData> {
        return repository.getPlaylist(id)
    }
}