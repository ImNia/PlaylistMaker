package com.delirium.playlistmaker.player.ui.models

data class PlayStatus(
    val progress: String,
    val isPlaying: Boolean,
    val playerStatus: PlayerState,
)