package com.delirium.playlistmaker.media.ui.fragment.favorite

import com.delirium.playlistmaker.media.domain.model.ModelAdapterFavorite

sealed interface FavoriteStateScreen {
    data class Content(
        val data: List<ModelAdapterFavorite>
    ): FavoriteStateScreen

    object NotFavorite: FavoriteStateScreen
}