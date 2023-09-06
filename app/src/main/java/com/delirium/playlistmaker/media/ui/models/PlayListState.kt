package com.delirium.playlistmaker.media.ui.models

import com.delirium.playlistmaker.media.domain.model.PlayListData

sealed interface PlayListState {
    class Content(
        val data: List<PlayListData>
    ): PlayListState
    object Empty: PlayListState
}