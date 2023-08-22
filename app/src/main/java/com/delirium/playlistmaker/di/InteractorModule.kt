package com.delirium.playlistmaker.di

import com.delirium.playlistmaker.player.domain.api.PlayerInteractor
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.domain.impl.PlayerInteractorImpl
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
}