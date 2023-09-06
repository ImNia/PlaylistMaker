package com.delirium.playlistmaker.media.domain.model

data class PlayListData(
    val name: String,
    val description: String? = null,
    val image: String? = null,
    val songList: String? = null,
    val countSong: Long = 0
)
