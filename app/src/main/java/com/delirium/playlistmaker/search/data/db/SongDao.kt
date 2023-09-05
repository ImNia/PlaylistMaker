package com.delirium.playlistmaker.search.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.delirium.playlistmaker.utils.db.SongEntity

@Dao
interface SongDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSong(song: SongEntity)

    @Query("SELECT * FROM song_table")
    suspend fun getSongs(): List<SongEntity>

    @Query("DELETE FROM song_table")
    suspend fun deleteSongs()

    @Query("SELECT * FROM song_table WHERE trackId = :songId")
    suspend fun getSong(songId: String): SongEntity?
}