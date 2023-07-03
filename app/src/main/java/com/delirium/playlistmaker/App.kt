package com.delirium.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.delirium.playlistmaker.di.dataModule
import com.delirium.playlistmaker.di.interactorModule
import com.delirium.playlistmaker.di.repositoryModule
import com.delirium.playlistmaker.di.viewModelModule
import com.delirium.playlistmaker.utils.model.SettingPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    var darkTheme = true
    lateinit var sharedPrefsTheme: SharedPreferences
    override fun onCreate() {
        super.onCreate()

        sharedPrefsTheme = getSharedPreferences(SettingPreferences.THEME_MODE.name, MODE_PRIVATE)
        darkTheme = sharedPrefsTheme.getBoolean(SettingPreferences.THEME_MODE.name, false)
        switchTheme(darkTheme)

        startKoin {
            androidContext(this@App)
            modules(
                dataModule, interactorModule, repositoryModule, viewModelModule
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