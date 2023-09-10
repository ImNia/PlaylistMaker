package com.delirium.playlistmaker.media.domain.impl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.delirium.playlistmaker.media.domain.api.StorageInteractor
import com.delirium.playlistmaker.media.domain.model.ResponseImageInfo
import java.io.File
import java.io.FileOutputStream

class StorageInteractorImpl(
    private val context: Context
): StorageInteractor {
    override fun saveImageToStorage(uri: Uri, filesDir: String): ResponseImageInfo {
        val filePath =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filesDir)
        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        val nameImage: String = (System.currentTimeMillis()/1000).toString()
        val file = File(filePath, nameImage)
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        return ResponseImageInfo(
            name = nameImage,
            filePath = file.toString()
        )
    }
}