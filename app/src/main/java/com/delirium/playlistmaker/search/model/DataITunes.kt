package com.delirium.playlistmaker.search.model

data class DataITunes(
    val resultCount: Int,
    val results: List<SongItem>
)
