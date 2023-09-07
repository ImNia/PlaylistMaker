package com.delirium.playlistmaker.player.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.delirium.playlistmaker.utils.db.SongPlaylistEntity

@Dao
interface SongPlaylistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSongForPlaylist(song: SongPlaylistEntity)
}