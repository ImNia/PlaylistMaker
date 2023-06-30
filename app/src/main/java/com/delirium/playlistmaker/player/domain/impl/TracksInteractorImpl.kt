package com.delirium.playlistmaker.player.domain.impl

import com.delirium.playlistmaker.player.domain.repository.SharingRepository
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import java.util.concurrent.Executors

class TracksInteractorImpl(
    private val repository: SharingRepository
) : TracksInteractor {
    private val executor = Executors.newCachedThreadPool()
    override fun prepareData(trackId: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            val track = repository.findSongInSharedPrefs(trackId)
            consumer.onComplete(track?.copy(artworkUrl100 = getCoverArtwork(track.artworkUrl100)))
        }
    }

    private fun getCoverArtwork(artworkUrl: String) =
        artworkUrl.replaceAfterLast('/', "512x512bb.jpg")

}