package com.delirium.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.delirium.playlistmaker.player.data.api.PlayerRepository
import com.delirium.playlistmaker.player.data.impl.PlayerRepositoryImpl
import com.delirium.playlistmaker.player.domain.api.TrackPlayer
import com.delirium.playlistmaker.player.domain.api.TracksInteractor
import com.delirium.playlistmaker.player.domain.impl.TrackPlayerImpl
import com.delirium.playlistmaker.player.domain.impl.TracksInteractorImpl
import com.delirium.playlistmaker.search.data.api.HistoryRepository
import com.delirium.playlistmaker.search.data.impl.HistoryRepositoryImpl
import com.delirium.playlistmaker.search.data.api.NetworkClient
import com.delirium.playlistmaker.search.data.impl.RetrofitClientImpl
import com.delirium.playlistmaker.search.domain.api.HistoryInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitInteractor
import com.delirium.playlistmaker.search.domain.api.RetrofitRepository
import com.delirium.playlistmaker.search.domain.impl.HistoryInteractorImpl
import com.delirium.playlistmaker.search.domain.impl.RetrofitInteractorImpl
import com.delirium.playlistmaker.search.domain.impl.RetrofitRepositoryImpl
import com.delirium.playlistmaker.settings.data.ExternalNavigator
import com.delirium.playlistmaker.settings.data.SettingsRepository
import com.delirium.playlistmaker.settings.data.SettingsRepositoryImpl
import com.delirium.playlistmaker.settings.domain.api.SettingsInteractor
import com.delirium.playlistmaker.settings.domain.api.SharingInteractor
import com.delirium.playlistmaker.settings.domain.impl.SettingsInteractorImpl
import com.delirium.playlistmaker.settings.domain.impl.SharingInteractorImpl
import com.delirium.playlistmaker.settings.models.SettingPreferences

class App : Application() {
    var darkTheme = true
    lateinit var sharedPrefsTheme: SharedPreferences
    lateinit var sharedPrefsFindSong: SharedPreferences
    override fun onCreate() {
        super.onCreate()

        sharedPrefsTheme = getSharedPreferences(SettingPreferences.THEME_MODE.name, MODE_PRIVATE)
        darkTheme = sharedPrefsTheme.getBoolean(SettingPreferences.THEME_MODE.name, false)
        switchTheme(darkTheme)

        sharedPrefsFindSong = getSharedPreferences(SettingPreferences.FINDING_SONG.name, MODE_PRIVATE)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getPlayerRepository())
    }
    private fun getPlayerRepository(): PlayerRepository {
        return PlayerRepositoryImpl(sharedPrefsFindSong)
    }
    fun provideTrackPlayer(): TrackPlayer {
        return TrackPlayerImpl()
    }

    fun providerSettingsInteractor(): SettingsInteractor {
        return SettingsInteractorImpl(getSettingsRepository())
    }

    private fun getSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl(sharedPrefsTheme, applicationContext)
    }

    fun providerSharingInteractor(): SharingInteractor {
        return SharingInteractorImpl(getExtrenalNavigator())
    }

    private fun getExtrenalNavigator(): ExternalNavigator {
        return ExternalNavigator()
    }

    fun providerRetrofitInteractor(): RetrofitInteractor {
        return RetrofitInteractorImpl(getRetrofitRepository())
    }

    private fun getRetrofitRepository(): RetrofitRepository {
        return RetrofitRepositoryImpl(getNetworkClient())
    }

    private fun getNetworkClient(): NetworkClient {
        return RetrofitClientImpl(
            context = applicationContext
        )
    }

    fun provideHistoryInteractor(): HistoryInteractor {
        return HistoryInteractorImpl(getHistoryRepository())
    }
    private fun getHistoryRepository(): HistoryRepository {
        return HistoryRepositoryImpl(sharedPrefsFindSong)
    }

}