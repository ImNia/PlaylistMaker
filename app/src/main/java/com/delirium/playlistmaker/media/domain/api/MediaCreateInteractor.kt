package com.delirium.playlistmaker.media.domain.api

import com.delirium.playlistmaker.media.domain.model.PlayListData
import kotlinx.coroutines.flow.Flow

interface MediaCreateInteractor {
    fun savePlayList(playList: PlayListData): Flow<Boolean>
    fun getPlayLists(): Flow<List<PlayListData>>
}