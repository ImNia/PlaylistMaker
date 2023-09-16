package com.delirium.playlistmaker.media.data.converters

import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.utils.db.PlayListEntity

class MediaDbConverters {
    fun map(entity: PlayListEntity): PlayListData {
        return PlayListData(
            id = entity.id,
            name = entity.name ?: "",
            description = entity.description ?: "",
            image = entity.image,
            songList = entity.songList,
            countSong = entity.countSong,
            filePath = entity.filePath,
            year = entity.year
        )
    }

    fun map(data: PlayListData): PlayListEntity {
        return PlayListEntity(
            id = data.id,
            name = data.name,
            description = data.description ?: "",
            image = data.image,
            songList = data.songList,
            countSong = data.countSong,
            filePath = data.filePath,
            year = data.year
        )
    }
}