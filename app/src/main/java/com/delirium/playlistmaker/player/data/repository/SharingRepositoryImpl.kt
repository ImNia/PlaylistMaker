package com.delirium.playlistmaker.player.data.repository

import android.content.SharedPreferences
import com.delirium.playlistmaker.player.data.converters.SongPlayerDbConverters
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.repository.SharingRepository
import com.delirium.playlistmaker.utils.db.AppDatabase
import com.delirium.playlistmaker.utils.model.SettingPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SharingRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
    private val appDatabase: AppDatabase,
    private val songDbConverter: SongPlayerDbConverters,
    private val gson: Gson,
) : SharingRepository {
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

    override fun findSongInDB(trackId: String): Flow<TrackModel?> = flow {
        val item = appDatabase.songPlayerDao().getSong(trackId)
        emit(songDbConverter.map(item))
    }
}