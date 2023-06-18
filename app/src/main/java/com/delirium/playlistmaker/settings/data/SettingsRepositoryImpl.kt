package com.delirium.playlistmaker.settings.data

import android.content.Context
import android.content.SharedPreferences
import com.delirium.playlistmaker.App
import com.delirium.playlistmaker.settings.data.models.ThemeSettings
import com.delirium.playlistmaker.settings.models.SettingPreferences

class SettingsRepositoryImpl(
    private val sharedPrefs: SharedPreferences,
    private val context: Context,
): SettingsRepository {
    override fun getThemeSettings(): ThemeSettings {
        return if (sharedPrefs.getBoolean(SettingPreferences.THEME_MODE.name, false))  {
            ThemeSettings(
                isNight = true
            )
        } else {
            ThemeSettings(
                isNight = false
            )
        }
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        sharedPrefs.edit()
            .putBoolean(SettingPreferences.THEME_MODE.name, settings.isNight)
            .apply()
        (context as App).switchTheme(
            sharedPrefs.getBoolean(
                SettingPreferences.THEME_MODE.name,
                false
            )
        )
    }
}