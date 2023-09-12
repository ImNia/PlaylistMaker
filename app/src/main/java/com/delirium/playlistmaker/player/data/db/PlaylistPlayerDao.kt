package com.delirium.playlistmaker.player.data.db

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.delirium.playlistmaker.utils.db.PlayListEntity

@Dao
interface PlaylistPlayerDao {
    @Query("SELECT * FROM play_list_table")
    suspend fun getPlaylists(): List<PlayListEntity>

    @Query("SELECT * FROM play_list_table WHERE id=:id")
    suspend fun getPlaylistById(id: Long): PlayListEntity
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeSongsInPlaylist(playlist: PlayListEntity)
}