package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.repository.SharingRepository
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.domain.model.TrackModel
import kotlinx.coroutines.flow.Flow

class TracksInteractorImpl(
    private val repository: SharingRepository
) : TracksInteractor {
    override fun prepareData(trackId: String): Flow<TrackModel?> {
        return repository.findSongInDB(trackId)
    }

    private fun getCoverArtwork(artworkUrl: String) =
        artworkUrl.replaceAfterLast('/', "512x512bb.jpg")

}