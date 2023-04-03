package com.delirium.playlistmaker.search.itunes.model

data class DataITunes(
    val resultCount: Int,
    val results: List<SongItem>
)
