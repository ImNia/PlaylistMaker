package com.delirium.playlistmaker.player.ui.models

sealed class PlayerState(val isPlayButtonEnabled: Boolean, var progress: String) {
    class Default : PlayerState(false, "00:00")
    class Error : PlayerState(false, "00:00")
    class Prepared : PlayerState(false, "00:00")
    class Playing(progress: String) : PlayerState(true, progress)
    class Paused(progress: String) : PlayerState(true, progress)
}