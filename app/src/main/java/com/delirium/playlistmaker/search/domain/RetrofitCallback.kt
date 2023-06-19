package com.delirium.playlistmaker.search.domain

import com.delirium.playlistmaker.search.data.models.ModelForAdapter

interface RetrofitCallback {
    fun successful(data: MutableList<ModelForAdapter>)
    fun error(data: MutableList<ModelForAdapter>)
}