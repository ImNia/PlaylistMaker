package com.delirium.playlistmaker.media.ui.fragment.favorite

import com.delirium.playlistmaker.media.domain.model.SongItemFavorite

sealed interface FavoriteStateScreen {
    data class Content(
        val data: List<SongItemFavorite>
    ): FavoriteStateScreen

    object NotFavorite: FavoriteStateScreen
}