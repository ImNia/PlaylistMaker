package com.delirium.playlistmaker.player.data.api

import com.delirium.playlistmaker.player.data.TrackModel

interface PlayerRepository {
    fun findSongInSharedPrefs(trackId: String): TrackModel?
}