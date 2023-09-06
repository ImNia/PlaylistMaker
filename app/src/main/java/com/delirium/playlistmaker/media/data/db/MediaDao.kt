package com.delirium.playlistmaker.media.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.delirium.playlistmaker.utils.db.PlayListEntity

@Dao
interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPlayList(playList: PlayListEntity)
}