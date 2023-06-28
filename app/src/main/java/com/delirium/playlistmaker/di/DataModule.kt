package com.delirium.playlistmaker.di

import android.content.Context
import com.delirium.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.delirium.playlistmaker.player.domain.repository.PlayerRepository
import com.delirium.playlistmaker.search.data.network.ITunesSetting
import com.delirium.playlistmaker.search.data.repository.HistoryRepositoryImpl
import com.delirium.playlistmaker.search.data.repository.RetrofitClientImpl
import com.delirium.playlistmaker.search.domain.repository.HistoryRepository
import com.delirium.playlistmaker.search.domain.repository.NetworkClient
import com.delirium.playlistmaker.settings.data.SettingsRepositoryImpl
import com.delirium.playlistmaker.settings.domain.api.SettingsRepository
import com.delirium.playlistmaker.sharing.data.impl.ExternalNavigatorImpl
import com.delirium.playlistmaker.sharing.domain.ExternalNavigator
import com.delirium.playlistmaker.utils.model.SettingPreferences
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSearchModule = module {
    single {
        ITunesSetting.itunesInstant
    }

    single {
        androidContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }

    factory { Gson() }

    single<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }

    single<NetworkClient> {
        RetrofitClientImpl(get(), androidContext())
    }
}

val dataPlayerModule = module {
    single {
        androidContext()
            .getSharedPreferences("local_storage", Context.MODE_PRIVATE)
    }

    factory {
        Gson()
    }

    single<PlayerRepository> {
        PlayerRepositoryImpl(get(), get())
    }
}

val dataSettingModule = module {
    single {
        androidContext()
            .getSharedPreferences(SettingPreferences.THEME_MODE.name, Context.MODE_PRIVATE)
    }

    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get())
    }
}

val dataSharingModule = module {
    single<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }
}