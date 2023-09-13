package com.delirium.playlistmaker.media.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.delirium.playlistmaker.utils.db.PlayListEntity
import com.delirium.playlistmaker.utils.db.SongPlaylistEntity

@Dao
interface MediaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createPlayList(playList: PlayListEntity)

    @Query("SELECT * FROM play_list_table")
    suspend fun getPlaylists(): List<PlayListEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun changePlaylist(playList: PlayListEntity)

    @Query("SELECT * FROM play_list_table WHERE id = :idPlaylist")
    suspend fun getPlaylist(idPlaylist: Long): PlayListEntity

    @Query("SELECT * FROM song_playlist_table WHERE trackId = :idTrack")
    suspend fun getSongPlaylist(idTrack: Long): SongPlaylistEntity

    @Query("DELETE FROM play_list_table WHERE id=:id")
    suspend fun deletePlaylist(id: Long)

    @Query("DELETE FROM song_playlist_table WHERE trackId=:trackId")
    suspend fun deleteSong(trackId: String)
}