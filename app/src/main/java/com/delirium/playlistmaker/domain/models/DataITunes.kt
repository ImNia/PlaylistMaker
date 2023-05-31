package com.delirium.playlistmaker.domain.models

data class DataITunes(
    val resultCount: Int,
    val results: List<SongItem>
)
