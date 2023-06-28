package com.delirium.playlistmaker.player.domain.repository

import com.delirium.playlistmaker.player.domain.model.TrackModel

interface PlayerRepository {
    fun findSongInSharedPrefs(trackId: String): TrackModel?
}