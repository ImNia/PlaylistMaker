package com.delirium.playlistmaker.di

import com.delirium.playlistmaker.media.data.converters.FavoriteSongDbConverters
import com.delirium.playlistmaker.media.data.converters.MediaDbConverters
import com.delirium.playlistmaker.media.data.converters.SongPlaylistDbConverters
import com.delirium.playlistmaker.player.data.converters.PlaylistDbConverters
import com.delirium.playlistmaker.player.data.converters.SongPlayerDbConverters
import com.delirium.playlistmaker.player.data.converters.SongPlaylistPlayerDbConverters
import com.delirium.playlistmaker.search.data.converters.SongDbConverters
import com.delirium.playlistmaker.search.domain.api.RetrofitRepository
import com.delirium.playlistmaker.search.domain.impl.RetrofitRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    /** Search
     * */
    single<RetrofitRepository> {
        RetrofitRepositoryImpl(get())
    }

    /** Database
     * */
    factory {
        SongDbConverters()
    }
    factory {
        SongPlayerDbConverters()
    }
    factory {
        FavoriteSongDbConverters()
    }
    factory {
        MediaDbConverters()
    }
    factory {
        PlaylistDbConverters()
    }
    factory {
        SongPlaylistPlayerDbConverters()
    }
    factory {
        SongPlaylistDbConverters()
    }
}
