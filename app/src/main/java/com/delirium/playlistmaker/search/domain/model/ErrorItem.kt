package com.delirium.playlistmaker.search.domain.model

import com.delirium.playlistmaker.R

data class ErrorItem (
    val res: Int = R.drawable.not_connect_search,
    val text: String,
    val textSub: String
) : ModelForAdapter