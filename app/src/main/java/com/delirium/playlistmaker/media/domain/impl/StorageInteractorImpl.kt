package com.delirium.playlistmaker.media.domain.impl

import android.net.Uri
import com.delirium.playlistmaker.media.domain.api.StorageInteractor
import com.delirium.playlistmaker.media.domain.model.ResponseImageInfo
import com.delirium.playlistmaker.media.domain.repository.StorageRepository

class StorageInteractorImpl(
    private val repository: StorageRepository
): StorageInteractor {
    override fun saveImageToStorage(uri: Uri, filesDir: String): ResponseImageInfo {
        val nameImage: String = (System.currentTimeMillis()/ COUNT_MILLIS).toString()

        return ResponseImageInfo(
            name = nameImage,
            filePath = repository.saveImageToStorage(nameImage, uri, filesDir)
        )
    }
    companion object {
        const val COUNT_MILLIS = 1000
    }
}