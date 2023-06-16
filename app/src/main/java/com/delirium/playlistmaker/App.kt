package com.delirium.playlistmaker

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.delirium.playlistmaker.presentation.SettingPreferences

class App: Application() {
    var darkTheme = true
    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = getSharedPreferences(SettingPreferences.THEME_MODE.name, MODE_PRIVATE)
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
}