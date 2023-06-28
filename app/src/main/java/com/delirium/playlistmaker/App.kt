package com.delirium.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.delirium.playlistmaker.di.dataPlayerModule
import com.delirium.playlistmaker.di.dataSearchModule
import com.delirium.playlistmaker.di.dataSettingModule
import com.delirium.playlistmaker.di.dataSharingModule
import com.delirium.playlistmaker.di.interactorPlayerModule
import com.delirium.playlistmaker.di.interactorSearchModule
import com.delirium.playlistmaker.di.interactorSettingModule
import com.delirium.playlistmaker.di.interactorSharingModule
import com.delirium.playlistmaker.di.repositorySearchModule
import com.delirium.playlistmaker.di.viewModelPlayerModule
import com.delirium.playlistmaker.di.viewModelSearchModule
import com.delirium.playlistmaker.di.viewModelSettingModule
import com.delirium.playlistmaker.utils.model.SettingPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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

        startKoin {
            androidContext(this@App)
            modules(
                dataSearchModule, interactorSearchModule, repositorySearchModule, viewModelSearchModule,
                dataPlayerModule, interactorPlayerModule, viewModelPlayerModule,
                dataSettingModule, interactorSettingModule, viewModelSettingModule,
                dataSharingModule, interactorSharingModule
            )
        }
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
}