package com.delirium.playlistmaker.media.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.media.domain.api.MediaCreateInteractor
import com.delirium.playlistmaker.media.domain.api.StorageInteractor
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.model.ResponseImageInfo
import com.delirium.playlistmaker.media.ui.models.MediaCreateState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Calendar
import kotlin.random.Random

class MediaCreateViewModel(
    private val interactor: MediaCreateInteractor,
    private val interactorStorage: StorageInteractor
) : ViewModel() {
    private var mediaCreateStatePlayerLiveData = MutableLiveData<MediaCreateState>()
    fun getMediaCreateStatePlayerLiveData(): MutableLiveData<MediaCreateState> = mediaCreateStatePlayerLiveData

    fun clickButtonCreate(name: String, description: String?, uri: Uri?) {
        viewModelScope.launch {
            val imageInfo = ResponseImageInfo(
                name = null,
                filePath = null
            )
            var job: Deferred<ResponseImageInfo>? = null
            uri?.let {
                job = async {
                    interactorStorage.saveImageToStorage(uri, FILES_DIR)
                }
            }

            job?.await().apply {
                imageInfo.name = this?.name
                imageInfo.filePath = this?.filePath
            }
            interactor.savePlayList(
                PlayListData(
                    id = Random.nextLong(),
                    name = name,
                    description = description,
                    image = imageInfo.name,
                    filePath = imageInfo.filePath,
                    year = Calendar.getInstance().get(Calendar.YEAR).toString()
                )
            ).collect { result ->
                if(result) {
                    mediaCreateStatePlayerLiveData.postValue(
                        MediaCreateState.Created(name = name)
                    )
                }
            }
        }
    }

    companion object {
        const val FILES_DIR = "playlist_maker_image"
    }
}