package com.delirium.playlistmaker.media.domain.repository

import com.delirium.playlistmaker.media.domain.model.SongItemFavorite
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun changeFavoriteState(song: SongItemFavorite): Flow<SongItemFavorite>
    fun getFavoriteSongs(): Flow<List<SongItemFavorite>>
}