package com.delirium.playlistmaker.media.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.media.domain.api.MediaCreateInteractor
import com.delirium.playlistmaker.media.ui.models.PlaylistMediaState
import kotlinx.coroutines.launch

class PlayListMediaViewModel(
    private val interactor: MediaCreateInteractor
) : ViewModel() {
    private val playListLiveData = MutableLiveData<PlaylistMediaState>()
    fun getPlayListLiveData(): MutableLiveData<PlaylistMediaState> = playListLiveData

    fun init() {
        viewModelScope.launch {
            interactor.getPlayLists().collect { result ->
                if (result.isEmpty()) {
                    playListLiveData.postValue(PlaylistMediaState.Empty)
                } else {
                    playListLiveData.postValue(
                        PlaylistMediaState.Content(data = result)
                    )
                }
            }
        }
    }
}