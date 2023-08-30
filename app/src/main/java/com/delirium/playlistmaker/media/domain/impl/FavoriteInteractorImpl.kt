package com.delirium.playlistmaker.media.domain.impl

import com.delirium.playlistmaker.media.domain.api.FavoriteInteractor
import com.delirium.playlistmaker.media.domain.model.SongItemFavorite
import com.delirium.playlistmaker.media.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class FavoriteInteractorImpl(
    private val favoriteRepository: FavoriteRepository
): FavoriteInteractor {
    override fun changeFavoriteState(song: SongItemFavorite): Flow<SongItemFavorite> {
        return favoriteRepository.changeFavoriteState(song)
    }
    override fun getFavoriteSongs(): Flow<List<SongItemFavorite>> {
        return favoriteRepository.getFavoriteSongs()
    }
}
