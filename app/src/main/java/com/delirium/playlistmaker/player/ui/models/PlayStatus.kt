package com.delirium.playlistmaker.player.ui.models

import com.delirium.playlistmaker.player.data.model.PlayerState

data class PlayStatus(
    val progress: String,
    val isPlaying: Boolean,
    val playerStatus: PlayerState,
)