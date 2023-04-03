package com.delirium.playlistmaker.search

import android.content.SharedPreferences
import com.delirium.playlistmaker.SettingPreferences
import com.delirium.playlistmaker.search.itunes.model.SongItem
import com.google.gson.Gson

object SongHistory {
    private const val MAX_SIZE_HISTORY = 10
    fun saveSong(sharedPrefs: SharedPreferences, song: SongItem) {
        val jsonNewHistory = Gson().toJson(formedHistory(sharedPrefs, song))
        sharedPrefs.edit()
            .putString(SettingPreferences.FINDING_SONG.name, jsonNewHistory)
            .apply()
    }

    private fun formedHistory(sharedPrefs: SharedPreferences, song: SongItem): ArrayList<SongItem> {
        val json = sharedPrefs.getString(SettingPreferences.FINDING_SONG.name, null)
        return if (json != null) {
            val oldHistory = Gson().fromJson(json, Array<SongItem>::class.java)
            val newHistory = arrayListOf<SongItem>()
            newHistory.add(song)
            for (item in oldHistory) {
                if (item != song && newHistory.size < MAX_SIZE_HISTORY) {
                    newHistory.add(item)
                }
            }
            newHistory
        } else {
            arrayListOf(song)
        }
    }

    fun getHistory(sharedPrefs: SharedPreferences): Array<SongItem> {
        val jsonHistory = sharedPrefs.getString(SettingPreferences.FINDING_SONG.name, null) ?: return arrayOf()
        return Gson().fromJson(jsonHistory, Array<SongItem>::class.java)
    }
}