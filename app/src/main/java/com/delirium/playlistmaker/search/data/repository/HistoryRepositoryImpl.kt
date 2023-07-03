package com.delirium.playlistmaker.search.data.repository

import android.content.SharedPreferences
import com.delirium.playlistmaker.search.domain.repository.HistoryRepository
import com.delirium.playlistmaker.search.domain.model.SongItem
import com.delirium.playlistmaker.utils.model.SettingPreferences
import com.google.gson.Gson

class HistoryRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
    private val gson: Gson,
): HistoryRepository {

    override fun saveSong(song: SongItem) {
        val jsonNewHistory = gson.toJson(formedHistory(song))
        sharedPrefs.edit()
            .putString(SettingPreferences.FINDING_SONG.name, jsonNewHistory)
            .apply()
    }

    override fun getHistory(): Array<SongItem> {
        val jsonHistory =
            sharedPrefs.getString(SettingPreferences.FINDING_SONG.name, null) ?: return arrayOf()
        return gson.fromJson(jsonHistory, Array<SongItem>::class.java)
    }
    override fun cleanHistory() {
        sharedPrefs.edit().clear().apply()
    }

    private fun formedHistory(song: SongItem): ArrayList<SongItem> {
        val json = sharedPrefs.getString(SettingPreferences.FINDING_SONG.name, null)
        return if (json != null) {
            val oldHistory = gson.fromJson(json, Array<SongItem>::class.java)
            val newHistory = arrayListOf<SongItem>()
            newHistory.add(song)
            for (item in oldHistory) {
                if (item.trackId != song.trackId && newHistory.size < MAX_SIZE_HISTORY) {
                    newHistory.add(item)
                }
            }
            newHistory
        } else {
            arrayListOf(song)
        }
    }

    companion object {
        const val MAX_SIZE_HISTORY = 10
    }
}