package com.delirium.playlistmaker.media.domain.repository

import android.net.Uri

interface StorageRepository {
    fun saveImageToStorage(nameImage: String, uri: Uri, filesDir: String): String
}