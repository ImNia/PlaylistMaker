package com.delirium.playlistmaker.media.data.db

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.delirium.playlistmaker.utils.db.SongEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM song_table WHERE trackId = :songId")
    suspend fun getSong(songId: String): SongEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeFavoriteState(song: SongEntity)

    @Query("SELECT * FROM song_table")
    suspend fun getSongs(): List<SongEntity>
}