package com.delirium.playlistmaker.di

import android.content.Context
import com.delirium.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.delirium.playlistmaker.player.domain.repository.PlayerRepository
import com.delirium.playlistmaker.search.data.network.ITunesServiceApi
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("http://itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ITunesServiceApi::class.java)
    }

    single {
        androidContext()
            .getSharedPreferences(SettingPreferences.THEME_MODE.name, Context.MODE_PRIVATE)
    }

    factory { Gson() }

    /** Search
     * */
    single<HistoryRepository> {
        HistoryRepositoryImpl(get(), get())
    }

    single<NetworkClient> {
        RetrofitClientImpl(get(), androidContext())
    }

    /** Player
     * */
    single<PlayerRepository> {
        PlayerRepositoryImpl(get(), get())
    }

    /** Setting
     * */
    single<SettingsRepository> {
        SettingsRepositoryImpl(get(), get())
    }

    /** Sharing
     * */
    single<ExternalNavigator> {
        ExternalNavigatorImpl(get())
    }
}