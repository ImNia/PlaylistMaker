package com.delirium.playlistmaker.search.ui.models

import com.delirium.playlistmaker.search.data.models.ErrorItem
import com.delirium.playlistmaker.search.data.models.NotFoundItem
import com.delirium.playlistmaker.search.data.models.SongListItem

sealed interface SearchState {
    object Loading: SearchState

    data class Content(
        val data: SongListItem,
    ): SearchState

    data class Error (
        val data: ErrorItem,
    ): SearchState

    data class Empty(
        val data: NotFoundItem,
    ): SearchState
}