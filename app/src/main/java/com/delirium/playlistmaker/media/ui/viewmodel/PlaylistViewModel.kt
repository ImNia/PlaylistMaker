package com.delirium.playlistmaker.media.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delirium.playlistmaker.media.domain.api.PlaylistInteractor
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.model.SongItemPlaylist
import com.delirium.playlistmaker.media.ui.models.PlaylistState
import com.delirium.playlistmaker.media.ui.models.ScreenState
import com.delirium.playlistmaker.media.ui.models.SongPlaylistState
import com.delirium.playlistmaker.sharing.model.ContentSharing
import com.delirium.playlistmaker.sharing.domain.SharingInteractor
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistViewModel(
    private val interactor: PlaylistInteractor,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {
    private val playlistStateLiveData = MutableLiveData<PlaylistState>()
    fun getPlaylistStateLiveData(): MutableLiveData<PlaylistState> = playlistStateLiveData

    private val songsStateLiveData = MutableLiveData<SongPlaylistState>()
    fun getSongsStateLiveData(): MutableLiveData<SongPlaylistState> = songsStateLiveData

    private val screenStateLiveData = MutableLiveData<ScreenState>()
    fun getScreenStateLiveData(): MutableLiveData<ScreenState> = screenStateLiveData

    private var currentIdPlaylist: Long? = null
    private var currentPlaylist: PlayListData? = null

    fun initData(id: String?) {
        if (id == null) {
            playlistStateLiveData.postValue(
                PlaylistState.Error
            )
        } else {
            currentIdPlaylist = id.toLong()
            viewModelScope.launch {
                interactor.getPlaylist(id.toLong()).collect {
                    playlistStateLiveData.postValue(
                        PlaylistState.Content(it)
                    )
                    currentPlaylist = it
                }
                interactor.getSongsPlaylist(id.toLong()).collect { songs ->
                    if (songs.isEmpty()) {
                        songsStateLiveData.postValue(SongPlaylistState.Empty)
                    } else {
                        songsStateLiveData.postValue(SongPlaylistState.Content(songs))
                    }
                }
            }
        }
    }

    fun deleteSongFromPlaylist(song: SongItemPlaylist) {
        viewModelScope.launch {
            val job = async {
                interactor.deleteSongPlaylist(song, currentIdPlaylist!!)
            }
            job.await().also {
                interactor.getPlaylist(currentIdPlaylist!!).collect {
                    playlistStateLiveData.postValue(
                        PlaylistState.Content(it)
                    )
                }
                interactor.getSongsPlaylist(currentIdPlaylist!!).collect { songs ->
                    if (songs.isEmpty()) {
                        songsStateLiveData.postValue(SongPlaylistState.Empty)
                    } else {
                        songsStateLiveData.postValue(SongPlaylistState.Content(songs))
                    }
                }
            }
        }
    }

    fun clickOnSharedIcon() {
        when (val songState = getSongsStateLiveData().value) {
            SongPlaylistState.Empty -> {
                screenStateLiveData.postValue(ScreenState.NotSongs)
            }

            is SongPlaylistState.Content -> {
                screenStateLiveData.postValue(
                    ScreenState.ShareSongs(
                        message = collectSongsList(songState.data)
                    )
                )
            }

            else -> {
                throw IllegalStateException()
            }
        }
    }

    private fun collectSongsList(songs: List<SongItemPlaylist>): String {
        val list = mutableListOf<String>()
        list.add("${currentPlaylist?.name}")
        list.add("${currentPlaylist?.description}")
        list.add("${songs.size} треков")
        songs.forEachIndexed { index, song ->
            list.add(
                "${index + 1}. ${song.artistName} - ${song.trackName} (${
                    SimpleDateFormat(
                        "mm:ss",
                        Locale.getDefault()
                    ).format(song.trackTimeMillis)
                })"
            )
        }
        return list.joinToString("\n")
    }

    fun sharing(content: ContentSharing) {
        sharingInteractor.openMessanger(content)
    }

    fun deletePlaylist() {
        viewModelScope.launch {
            val job = async {
                interactor.deletePlaylist(currentIdPlaylist!!)
            }
            job.await().also {
                songsStateLiveData.value.also { state ->
                    if(state is SongPlaylistState.Content) {
                        interactor.deleteSongs(state.data)
                    }
                }
                screenStateLiveData.postValue(
                    ScreenState.CloseScreen
                )
            }
        }
    }

    fun openEditScreen() {
        if(currentIdPlaylist != null) {
            screenStateLiveData.postValue(
                ScreenState.EditPlaylist(currentIdPlaylist!!)
            )
        }
    }
}