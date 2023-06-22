package com.delirium.playlistmaker.search.domain.model

import com.delirium.playlistmaker.search.data.models.Response

data class SongsSearchResponse(
    val resultCount: Int,
    val expression: String,
    val results: List<SongItem>
): Response()