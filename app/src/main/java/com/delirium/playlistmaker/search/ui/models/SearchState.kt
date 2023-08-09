package com.delirium.playlistmaker.search.ui.models

import com.delirium.playlistmaker.search.domain.model.SongItem
import com.delirium.playlistmaker.search.domain.model.SongListItem

sealed interface SearchState {
    object Loading: SearchState

    data class Content(
        val data: SongListItem,
    ): SearchState

    object Error: SearchState

    object Empty: SearchState

    data class History(
        val data: List<SongItem>
    ): SearchState
}