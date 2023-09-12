package com.delirium.playlistmaker.media.domain.api

import com.delirium.playlistmaker.media.domain.model.PlayListData
import kotlinx.coroutines.flow.Flow

interface PlaylistInteractor {
    fun getPlaylist(id: Long): Flow<PlayListData>
}