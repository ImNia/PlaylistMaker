package com.delirium.playlistmaker.media.domain.repository

import com.delirium.playlistmaker.media.domain.model.PlayListData
import kotlinx.coroutines.flow.Flow

interface MediaCreateRepository {
    fun createMediaPlayer(playList: PlayListData): Flow<Boolean>
    fun getPlayLists(): Flow<List<PlayListData>>
    fun getPlaylist(idPlaylist: Long): Flow<PlayListData>
}