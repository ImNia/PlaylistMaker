package com.delirium.playlistmaker.media.domain.repository

import com.delirium.playlistmaker.media.domain.model.PlayListData
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getPlaylist(id: Long): Flow<PlayListData>
}