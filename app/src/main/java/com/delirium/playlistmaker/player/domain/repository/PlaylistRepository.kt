package com.delirium.playlistmaker.player.domain.repository

import com.delirium.playlistmaker.player.ui.models.PlayListData
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun getPlaylists(): Flow<List<PlayListData>>
}