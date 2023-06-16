package com.delirium.playlistmaker.domain.models

import com.delirium.playlistmaker.R

data class ErrorItem (
    val res: Int = R.drawable.not_connect_search,
    val text: String,
    val textSub: String
) : AdapterModel