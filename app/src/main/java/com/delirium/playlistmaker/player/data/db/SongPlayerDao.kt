package com.delirium.playlistmaker.player.data.db

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SongPlayerDao {

    @Query("SELECT * FROM song_table WHERE trackId = :songId")
    suspend fun getSong(songId: String): SongEntity
}