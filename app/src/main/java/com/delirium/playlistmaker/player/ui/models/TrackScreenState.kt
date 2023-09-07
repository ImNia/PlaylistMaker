package com.delirium.playlistmaker.player.ui.models

import com.delirium.playlistmaker.player.domain.model.PlayListData
import com.delirium.playlistmaker.player.domain.model.TrackModel

sealed class TrackScreenState {
    object Loading: TrackScreenState()
    data class Content(
        val trackModel: TrackModel,
    ): TrackScreenState()

    object PlayerNotPrepared: TrackScreenState()

    data class BottomSheetShow(
        val data: List<PlayListData>
    ): TrackScreenState()
}