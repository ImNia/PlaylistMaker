package com.delirium.playlistmaker.search.data.models

import com.delirium.playlistmaker.R

data class NotFoundItem(
    val res: Int = R.drawable.not_search,
    val textProblem: String
) : ModelForAdapter
