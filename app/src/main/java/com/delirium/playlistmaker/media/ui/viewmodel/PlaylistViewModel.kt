package com.delirium.playlistmaker.media.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.media.domain.api.PlaylistInteractor
import com.delirium.playlistmaker.media.ui.models.PlaylistState
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val interactor: PlaylistInteractor
): ViewModel() {
    private val playlistStateLiveData = MutableLiveData<PlaylistState>()
    fun getPlaylistStateLiveData(): MutableLiveData<PlaylistState> = playlistStateLiveData

    fun initData(id: String?) {
        if(id == null) {
            playlistStateLiveData.postValue(
                PlaylistState.Error
            )
        } else {
            viewModelScope.launch {
                interactor.getPlaylist(id.toLong()).collect {
                    playlistStateLiveData.postValue(
                        PlaylistState.Content(it)
                    )
                }
            }
        }
    }
}