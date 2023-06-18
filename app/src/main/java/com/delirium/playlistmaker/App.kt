package com.delirium.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.delirium.playlistmaker.domain.api.TracksInteractor
import com.delirium.playlistmaker.domain.impl.TracksInteractorImpl
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
    lateinit var sharedPrefs: SharedPreferences
    override fun onCreate() {
        super.onCreate()

        sharedPrefs = getSharedPreferences(SettingPreferences.THEME_MODE.name, MODE_PRIVATE)
        darkTheme = sharedPrefs.getBoolean(SettingPreferences.THEME_MODE.name, false)
        switchTheme(darkTheme)
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

    /*fun getRepository(): TracksRepositoryImpl {
        TracksRepositoryImpl(NetworkClientImpl())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getRepository())
    }*/

    fun providerSettingsInteractor(): SettingsInteractor {
        return SettingsInteractorImpl(getSettingsRepository())
    }

    private fun getSettingsRepository(): SettingsRepository {
        return SettingsRepositoryImpl(sharedPrefs, applicationContext)
    }

    fun providerSharingInteractor(): SharingInteractor {
        return SharingInteractorImpl(getExtrenalNavigator())
    }

    private fun getExtrenalNavigator(): ExternalNavigator {
        return ExternalNavigator()
    }
}