package com.delirium.playlistmaker.player.data.repository

import android.content.SharedPreferences
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.PlayerRepository
import com.delirium.playlistmaker.utils.model.SettingPreferences
import com.google.gson.Gson

class PlayerRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson,
) : PlayerRepository {
    override fun findSongInSharedPrefs(trackId: String): TrackModel? {
        val jsonHistory =
            sharedPrefs.getString(SettingPreferences.FINDING_SONG.name, null)
        val allSong = gson.fromJson(jsonHistory, Array<TrackModel>::class.java)
        for (item in allSong) {
            if (item.trackId == trackId) {
                return item
            }
        }
        return null
    }
}