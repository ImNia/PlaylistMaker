package com.delirium.playlistmaker.searchitunes.model

data class DataITunes(
    val resultCount: Int,
    val results: List<SongItem>
)
