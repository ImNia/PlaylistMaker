package com.delirium.playlistmaker.player.domain.api

import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.model.PlayListData
import kotlinx.coroutines.flow.Flow

interface PlaylistPlayerInteractor {
    fun getPlaylists(): Flow<List<PlayListData>>
    suspend fun saveSong(song: TrackModel)
    suspend fun addSongToPlaylist(playlist: PlayListData, song: TrackModel)
}