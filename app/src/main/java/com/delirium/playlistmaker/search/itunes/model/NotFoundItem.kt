package com.delirium.playlistmaker.search.itunes.model

import com.delirium.playlistmaker.R

data class NotFoundItem(
    val res: Int = R.drawable.not_search,
    val textProblem: String
) : AdapterModel
