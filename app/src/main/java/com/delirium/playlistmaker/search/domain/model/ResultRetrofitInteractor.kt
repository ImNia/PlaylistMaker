package com.delirium.playlistmaker.search.domain.model

data class ResultRetrofitInteractor(
    val listSong: List<SongItem>? = null,
    val error: String? = null
)
