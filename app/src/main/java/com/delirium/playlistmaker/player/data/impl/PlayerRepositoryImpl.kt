package com.delirium.playlistmaker.player.data.impl

import android.content.SharedPreferences
import com.delirium.playlistmaker.player.data.TrackModel
import com.delirium.playlistmaker.player.data.api.PlayerRepository
import com.delirium.playlistmaker.search.data.models.SongItem
import com.delirium.playlistmaker.settings.models.SettingPreferences
import com.google.gson.Gson

class PlayerRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
) : PlayerRepository {
    override fun findSongInSharedPrefs(trackId: String): TrackModel? {
        val jsonHistory =
            sharedPrefs.getString(SettingPreferences.FINDING_SONG.name, null)
        val allSong = Gson().fromJson(jsonHistory, Array<SongItem>::class.java)
        for (item in allSong) {
            if (item.trackId == trackId) {
                return convertToTrackModel(item)
            }
        }
        return null
    }

    private fun convertToTrackModel(item: SongItem): TrackModel = TrackModel(
        trackId = item.trackId,
        trackName = item.trackName,
        artistName = item.artistName,
        collectionName = item.collectionName,
        trackTimeMillis = item.trackTimeMillis,
        artworkUrl100 = getCoverArtwork(item.artworkUrl100),
        releaseDate = item.releaseDate,
        country = item.country,
        primaryGenreName = item.primaryGenreName,
        previewUrl = item.previewUrl,
    )

    private fun getCoverArtwork(artworkUrl: String) =
        artworkUrl.replaceAfterLast('/', "512x512bb.jpg")

}