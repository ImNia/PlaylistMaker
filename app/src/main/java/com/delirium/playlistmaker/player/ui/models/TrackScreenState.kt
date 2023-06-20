package com.delirium.playlistmaker.player.ui.models

import com.delirium.playlistmaker.player.data.TrackModel

sealed class TrackScreenState {
    object Loading: TrackScreenState()
    data class Content(
        val trackModel: TrackModel,
    ): TrackScreenState()
}