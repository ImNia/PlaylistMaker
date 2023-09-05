package com.delirium.playlistmaker.player.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.delirium.playlistmaker.utils.db.FavoriteSongEntity
import com.delirium.playlistmaker.utils.db.SongEntity

@Dao
interface SongPlayerDao {
    @Query("SELECT * FROM song_table WHERE trackId = :songId")
    suspend fun getSong(songId: String): SongEntity?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeFavoriteState(song: FavoriteSongEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteSong(song: FavoriteSongEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeFavoriteStateHistory(song: SongEntity)

    @Query("SELECT * FROM favorite_song_table WHERE trackId = :songId")
    suspend fun getFavoriteSong(songId: String): FavoriteSongEntity?

}