package com.delirium.playlistmaker.domain.repository

import com.delirium.playlistmaker.domain.models.AdapterModel

interface RetrofitCallback {
    fun successful(data: MutableList<AdapterModel>)
    fun error(data: MutableList<AdapterModel>)
}