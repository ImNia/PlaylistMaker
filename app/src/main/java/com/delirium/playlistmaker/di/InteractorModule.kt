package com.delirium.playlistmaker.di

import com.delirium.playlistmaker.player.domain.api.TracksInteractor
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

val interactorSearchModule = module {
    single<RetrofitInteractor> {
        RetrofitInteractorImpl(get())
    }
    single<HistoryInteractor> {
        HistoryInteractorImpl(get())
    }
}

val interactorPlayerModule = module {
    single<TracksInteractor> {
        TracksInteractorImpl(get())
    }
}

val interactorSettingModule = module {
    single<SettingsInteractor> {
        SettingsInteractorImpl(get())
    }
}

val interactorSharingModule = module {
    single<SharingInteractor> {
        SharingInteractorImpl(get())
    }
}