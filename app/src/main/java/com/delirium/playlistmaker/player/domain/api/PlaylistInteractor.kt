package com.delirium.playlistmaker.player.domain.api

import com.delirium.playlistmaker.player.ui.models.PlayListData
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {
    fun getPlaylists(): Flow<List<PlayListData>>
}