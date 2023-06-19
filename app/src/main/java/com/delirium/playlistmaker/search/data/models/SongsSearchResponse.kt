package com.delirium.playlistmaker.search.data.models

data class SongsSearchResponse(
    val resultCount: Int,
    val expression: String,
    val results: List<SongItem>
): Response()