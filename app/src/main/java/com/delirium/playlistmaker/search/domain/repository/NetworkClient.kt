package com.delirium.playlistmaker.search.domain.repository

import com.delirium.playlistmaker.search.data.models.Response

interface NetworkClient {
    fun getSongs(dto: Any): Response
}