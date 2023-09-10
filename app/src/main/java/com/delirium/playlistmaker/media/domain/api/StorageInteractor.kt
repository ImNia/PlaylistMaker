package com.delirium.playlistmaker.media.domain.api

import android.net.Uri
import com.delirium.playlistmaker.media.domain.model.ResponseImageInfo

interface StorageInteractor {
    fun saveImageToStorage(uri: Uri, filesDir: String): ResponseImageInfo
}