package com.delirium.playlistmaker.player.ui.models

data class PlayListData(
    val id: Long,
    val name: String,
    val description: String? = null,
    val image: String? = null,
    val songList: String? = null,
    val countSong: Long = 0
)
