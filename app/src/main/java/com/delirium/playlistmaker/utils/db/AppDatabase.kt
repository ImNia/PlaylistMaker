package com.delirium.playlistmaker.utils.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.delirium.playlistmaker.player.data.db.SongPlayerDao
import com.delirium.playlistmaker.search.data.db.SongDao
import com.delirium.playlistmaker.search.data.db.SongEntity

@Database(version = 1, entities = [SongEntity::class])
abstract class AppDatabase: RoomDatabase() {
    abstract fun songDao(): SongDao
    abstract fun songPlayerDao(): SongPlayerDao
}