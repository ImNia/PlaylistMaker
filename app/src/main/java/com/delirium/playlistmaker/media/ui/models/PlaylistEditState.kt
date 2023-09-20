package com.delirium.playlistmaker.media.ui.models

import com.delirium.playlistmaker.media.domain.model.PlayListData

sealed interface PlaylistEditState {
    data class Content(
        val playlist: PlayListData
    ): PlaylistEditState
    data class CloseScreen(
        val updated: Boolean,
        val playlist: PlayListData
    ): PlaylistEditState
}