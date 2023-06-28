package com.delirium.playlistmaker.search.ui.models

import com.delirium.playlistmaker.search.domain.model.ErrorItem
import com.delirium.playlistmaker.search.domain.model.NotFoundItem
import com.delirium.playlistmaker.search.domain.model.SongListItem

sealed interface SearchState {
    object Loading: SearchState

    data class Content(
        val data: SongListItem,
    ): SearchState

    object Error: SearchState

    object Empty: SearchState
}