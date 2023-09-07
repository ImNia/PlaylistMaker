package com.delirium.playlistmaker.player.data.db

import androidx.room.Dao
import androidx.room.Query
import com.delirium.playlistmaker.utils.db.PlayListEntity

@Dao
interface PlaylistPlayerDao {
    @Query("SELECT * FROM play_list_table")
    suspend fun getPlaylists(): List<PlayListEntity>
}