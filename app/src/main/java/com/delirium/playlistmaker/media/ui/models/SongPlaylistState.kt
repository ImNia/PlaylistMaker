package com.delirium.playlistmaker.media.ui.models

import com.delirium.playlistmaker.media.domain.model.SongItemPlaylist

sealed interface SongPlaylistState{
    class Content(
        val data: List<SongItemPlaylist>
    ): SongPlaylistState
    object Empty: SongPlaylistState
}