package com.delirium.playlistmaker.search.data.api

import com.delirium.playlistmaker.search.data.models.Response

interface NetworkClient {
    fun getSongs(dto: Any): Response
}