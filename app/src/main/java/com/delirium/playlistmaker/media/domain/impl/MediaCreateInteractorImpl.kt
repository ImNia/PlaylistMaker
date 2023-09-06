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
}