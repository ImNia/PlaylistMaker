package com.delirium.playlistmaker.media.data.db

import androidx.room.Dao
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.delirium.playlistmaker.utils.db.FavoriteSongEntity

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_song_table WHERE trackId = :songId")
    suspend fun getSong(songId: String): FavoriteSongEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changeFavoriteState(song: FavoriteSongEntity)

    @Query("SELECT * FROM favorite_song_table WHERE isFavorite == 1")
    suspend fun getSongs(): List<FavoriteSongEntity>
}