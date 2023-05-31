package com.delirium.playlistmaker.domain.repository

import com.delirium.playlistmaker.domain.models.AdapterModel

interface RetrofitRepository {
    fun getSongs(nameSong: String): MutableList<AdapterModel>
}