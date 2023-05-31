package com.delirium.playlistmaker.data.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.delirium.playlistmaker.domain.models.SongItem
import com.delirium.playlistmaker.domain.repository.HistoryRepository
import com.delirium.playlistmaker.presentation.SettingPreferences
import com.google.gson.Gson

class HistoryRepositoryImpl(private val context: Context): HistoryRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(SettingPreferences.FINDING_SONG.name,
        AppCompatActivity.MODE_PRIVATE
    )

    override fun saveSong(song: SongItem) {
        val jsonNewHistory = Gson().toJson(formedHistory(song))
        sharedPreferences.edit()
            .putString(SettingPreferences.FINDING_SONG.name, jsonNewHistory)
            .apply()
    }

    override fun getHistory(): Array<SongItem> {
        val jsonHistory =
            sharedPreferences.getString(SettingPreferences.FINDING_SONG.name, null) ?: return arrayOf()
        return Gson().fromJson(jsonHistory, Array<SongItem>::class.java)
    }
    override fun cleanHistory() {
        sharedPreferences.edit().clear().apply()
    }

    private fun formedHistory(song: SongItem): ArrayList<SongItem> {
        val json = sharedPreferences.getString(SettingPreferences.FINDING_SONG.name, null)
        return if (json != null) {
            val oldHistory = Gson().fromJson(json, Array<SongItem>::class.java)
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