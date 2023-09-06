package com.delirium.playlistmaker.media.data.converters

import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.utils.db.PlayListEntity
import kotlin.random.Random

class MediaDbConverters {
    fun map(entity: PlayListEntity): PlayListData {
        return PlayListData(
            name = entity.name ?: "",
            description = entity.description,
            image = entity.image
        )
    }

    fun map(data: PlayListData): PlayListEntity {
        return PlayListEntity(
            id = Random.nextLong(),
            name = data.name,
            description = data.description,
            image = data.image,
        )
    }
}