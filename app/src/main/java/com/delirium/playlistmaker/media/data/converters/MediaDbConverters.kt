package com.delirium.playlistmaker.media.data.converters

import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.utils.db.PlayListEntity
import kotlin.random.Random

class MediaDbConverters {
    fun map(entity: PlayListEntity): PlayListData {
        return PlayListData(
            name = entity.name ?: "",
            description = entity.description,
            image = entity.image,
            songList = entity.songList,
            countSong = entity.countSong
        )
    }

    fun map(data: PlayListData): PlayListEntity {
        return PlayListEntity(
            id = Random.nextLong(),
            name = data.name,
            description = data.description,
            image = data.image,
            songList = data.songList,
            countSong = data.countSong
        )
    }
}