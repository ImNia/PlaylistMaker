package com.delirium.playlistmaker.media.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.media.domain.api.MediaCreateInteractor
import com.delirium.playlistmaker.media.ui.models.PlayListState
import kotlinx.coroutines.launch

class PlayListMediaViewModel(
    private val interactor: MediaCreateInteractor
) : ViewModel() {
    private val playListLiveData = MutableLiveData<PlayListState>()
    fun getPlayListLiveData(): MutableLiveData<PlayListState> = playListLiveData

    fun init() {
        viewModelScope.launch {
            interactor.getPlayLists().collect { result ->
                if (result.isEmpty()) {
                    playListLiveData.postValue(PlayListState.Empty)
                } else {
                    playListLiveData.postValue(
                        PlayListState.Content(data = result)
                    )
                }
            }
        }
    }
}