package com.delirium.playlistmaker.media.ui.models

sealed interface MediaCreateState {
    class Created(
        val name: String
    ): MediaCreateState
}