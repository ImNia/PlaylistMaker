package com.delirium.playlistmaker.media.domain.impl

import com.delirium.playlistmaker.media.domain.api.MediaCreateInteractor
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.repository.MediaCreateRepository
import kotlinx.coroutines.flow.Flow

class MediaCreateInteractorImpl(
    private val repository: MediaCreateRepository
): MediaCreateInteractor {
    override fun savePlayList(playList: PlayListData): Flow<Boolean> {
        return repository.createMediaPlayer(playList)
    }

    override fun getPlayLists(): Flow<List<PlayListData>> {
        return repository.getPlayLists()
    }

    override fun getPlaylist(idPlaylist: Long): Flow<PlayListData> {
        return repository.getPlaylist(idPlaylist)
    }
}