package com.delirium.playlistmaker.media.ui.models

sealed interface ScreenState {
    object NotSongs: ScreenState
    data class ShareSongs(
        val message: String
    ): ScreenState
    object CloseScreen: ScreenState
    data class EditPlaylist(
        val idPlaylist: Long
    ): ScreenState
}