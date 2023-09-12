package com.delirium.playlistmaker.di

import com.delirium.playlistmaker.media.domain.api.FavoriteInteractor
import com.delirium.playlistmaker.media.domain.api.MediaCreateInteractor
import com.delirium.playlistmaker.media.domain.api.PlaylistInteractor
import com.delirium.playlistmaker.media.domain.api.StorageInteractor
import com.delirium.playlistmaker.media.domain.impl.FavoriteInteractorImpl
import com.delirium.playlistmaker.media.domain.impl.MediaCreateInteractorImpl
import com.delirium.playlistmaker.media.domain.impl.PlaylistInteractorImpl
import com.delirium.playlistmaker.media.domain.impl.StorageInteractorImpl
import com.delirium.playlistmaker.player.domain.api.PlayerInteractor
import com.delirium.playlistmaker.player.domain.api.PlaylistPlayerInteractor
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.domain.impl.PlayerInteractorImpl
import com.delirium.playlistmaker.player.domain.impl.PlaylistPlayerInteractorImpl
import com.delirium.playlistmaker.player.domain.impl.TracksInteractorImpl
import com.delirium.playlistmaker.search.domain.api.HistoryInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitInteractor
import com.delirium.playlistmaker.search.domain.impl.HistoryInteractorImpl
import com.delirium.playlistmaker.search.domain.impl.RetrofitInteractorImpl
import com.delirium.playlistmaker.settings.domain.api.SettingsInteractor
import com.delirium.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.delirium.playlistmaker.sharing.domain.SharingInteractor
import com.delirium.playlistmaker.sharing.domain.impl.SharingInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    /** Search
     * */
    single<RetrofitInteractor> {
        RetrofitInteractorImpl(get())
    }
    single<HistoryInteractor> {
        HistoryInteractorImpl(get())
    }

    /** Player
     * */
    single<TracksInteractor> {
        TracksInteractorImpl(get())
    }
    single<PlayerInteractor> {
        PlayerInteractorImpl(get())
    }
    single<PlaylistPlayerInteractor> {
        PlaylistPlayerInteractorImpl(get())
    }

    /** Setting
     * */
    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }

    /** Sharing
     * */
    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }

    /** Media
     * */
    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }
    single<MediaCreateInteractor> {
        MediaCreateInteractorImpl(get())
    }
    single<StorageInteractor> {
        StorageInteractorImpl(get())
    }
    single<PlaylistInteractor> {
        PlaylistInteractorImpl(get())
    }
}