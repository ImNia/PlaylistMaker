package com.delirium.playlistmaker.media.domain.api

import com.delirium.playlistmaker.media.domain.model.SongItemFavorite
import kotlinx.coroutines.flow.Flow

interface FavoriteInteractor {
    fun changeFavoriteState(song: SongItemFavorite): Flow<SongItemFavorite>
    fun getFavoriteSongs(): Flow<List<SongItemFavorite>>
}