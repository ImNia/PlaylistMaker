package com.delirium.playlistmaker.media.ui.models

import com.delirium.playlistmaker.media.domain.model.PlayListData

sealed interface PlaylistMediaState {
    class Content(
        val data: List<PlayListData>
    ): PlaylistMediaState
    object Empty: PlaylistMediaState
}