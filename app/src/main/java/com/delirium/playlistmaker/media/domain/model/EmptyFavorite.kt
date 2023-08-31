package com.delirium.playlistmaker.media.domain.model

import com.delirium.playlistmaker.R

data class EmptyFavorite(
    val res: Int = R.drawable.not_search,
    val textProblem: Int = R.string.media_empty
): ModelAdapterFavorite
