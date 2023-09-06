package com.delirium.playlistmaker.media.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.media.domain.api.MediaCreateInteractor
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.ui.models.MediaCreateState
import kotlinx.coroutines.launch

class MediaCreateViewModel(
    private val interactor: MediaCreateInteractor
) : ViewModel() {
    private var mediaCreateStatePlayerLiveData = MutableLiveData<MediaCreateState>()
    fun getMediaCreateStatePlayerLiveData(): MutableLiveData<MediaCreateState> = mediaCreateStatePlayerLiveData

    fun clickButtonCreate(name: String, description: String?, image: String?) {
        viewModelScope.launch {
            interactor.savePlayList(
                PlayListData(
                    name = name,
                    description = description,
                    image = image
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
}