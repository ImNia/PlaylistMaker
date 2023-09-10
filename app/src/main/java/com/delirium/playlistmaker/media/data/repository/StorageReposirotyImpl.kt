package com.delirium.playlistmaker.media.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import com.delirium.playlistmaker.media.domain.repository.StorageRepository
import java.io.File
import java.io.FileOutputStream

class StorageReposirotyImpl(
    private val context: Context
): StorageRepository {
    override fun saveImageToStorage(nameImage: String, uri: Uri, filesDir: String): String {
        val filePath =
            File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), filesDir)
        if (!filePath.exists()) {
            filePath.mkdirs()
        }

        val file = File(filePath, nameImage)
        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
        return file.toString()
    }
}