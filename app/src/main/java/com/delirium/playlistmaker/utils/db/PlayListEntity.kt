package com.delirium.playlistmaker.utils.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "play_list_table")
data class PlayListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
    val songList: String? = null,
    val countSong: Long = 0,
    val filePath: String? = null,
    val year: String? = null
)
