package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.repository.DatabaseRepository
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class TracksInteractorImpl(
    private val repository: DatabaseRepository
) : TracksInteractor {
    override fun prepareData(trackId: String): Flow<TrackModel?> {
        return repository.findSongInDB(trackId)
    }

    override fun changeFavoriteState(trackId: String): Flow<TrackModel> {
        return repository.changeFavoriteState(trackId)
    }
}