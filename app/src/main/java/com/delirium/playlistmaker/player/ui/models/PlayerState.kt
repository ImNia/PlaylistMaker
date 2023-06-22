package com.delirium.playlistmaker.player.ui.models

enum class PlayerState(value: Int) {
    STATE_DEFAULT(0),
    STATE_PREPARED(1),
    STATE_PLAYING(2),
    STATE_PAUSED(3),
}