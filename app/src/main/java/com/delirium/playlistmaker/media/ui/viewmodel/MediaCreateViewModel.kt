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
import com.delirium.playlistmaker.media.ui.models.PlaylistEditState
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
    fun getMediaCreateStatePlayerLiveData(): MutableLiveData<MediaCreateState> =
        mediaCreateStatePlayerLiveData

    private var playlistStateLiveData = MutableLiveData<PlaylistEditState>()
    fun getPlaylistStateLiveData(): MutableLiveData<PlaylistEditState> = playlistStateLiveData

    private var currentPlaylist: PlayListData? = null
    fun clickButtonCreate(idPlaylist: Long? = null, name: String, description: String?, uri: Uri?) {
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
                    id = idPlaylist ?: Random.nextLong(),
                    name = currentPlaylist?.name ?: name,
                    description = currentPlaylist?.description ?: description,
                    image = imageInfo.name,
                    filePath = imageInfo.filePath,
                    songList = currentPlaylist?.songList ?:  null,
                    countSong = currentPlaylist?.countSong ?: 0L,
                    year = currentPlaylist?.year ?: Calendar.getInstance().get(Calendar.YEAR).toString()
                )
            ).collect { result ->
                if (result) {
                    mediaCreateStatePlayerLiveData.postValue(
                        MediaCreateState.Created(name = name)
                    )
                }
            }
        }
    }

    fun initData(idPlaylist: Long) {
        viewModelScope.launch {
            interactor.getPlaylist(idPlaylist).collect { playlist ->
                currentPlaylist = playlist
                playlistStateLiveData.postValue(
                    PlaylistEditState.Content(playlist)
                )
            }
        }
    }

    fun closeScreen(name: String?, description: String?, img: Uri?) {
        val playlistState = playlistStateLiveData.value
        if (playlistState is PlaylistEditState.Content) {
            playlistStateLiveData.postValue(
                PlaylistEditState.CloseScreen(
                    isEdited(
                        name,
                        description,
                        img,
                        playlistState.playlist
                    )
                )
            )
        }
    }

    private fun isEdited(
        name: String?,
        description: String?,
        img: Uri?,
        playlistData: PlayListData
    ): Boolean {
        return (name != null && name != playlistData.name
                || description != null && description != playlistData.description
                || img != null && img.toString() != playlistData.image)
    }

    companion object {
        const val FILES_DIR = "playlist_maker_image"
    }
}