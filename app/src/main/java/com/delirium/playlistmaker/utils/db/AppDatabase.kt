package com.delirium.playlistmaker.utils.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delirium.playlistmaker.media.data.db.FavoriteDao
import com.delirium.playlistmaker.media.data.db.MediaDao
import com.delirium.playlistmaker.player.data.db.PlaylistPlayerDao
import com.delirium.playlistmaker.player.data.db.SongPlayerDao
import com.delirium.playlistmaker.search.data.db.SongDao

@Database(version = 1, entities = [SongEntity::class, FavoriteSongEntity::class, PlayListEntity::class])
abstract class AppDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun songPlayerDao(): SongPlayerDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun mediaDao(): MediaDao
    abstract fun playlistPlayerDao(): PlaylistPlayerDao
}