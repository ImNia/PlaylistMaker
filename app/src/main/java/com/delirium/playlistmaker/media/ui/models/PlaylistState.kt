package com.delirium.playlistmaker.media.ui.models

import com.delirium.playlistmaker.media.domain.model.PlayListData

sealed interface PlaylistState {
    class Content(
        val data: PlayListData
    ): PlaylistState
    object Error: PlaylistState
}