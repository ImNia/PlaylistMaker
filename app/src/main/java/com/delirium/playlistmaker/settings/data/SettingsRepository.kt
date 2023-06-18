package com.delirium.playlistmaker.settings.data

import com.delirium.playlistmaker.settings.data.models.ThemeSettings

interface SettingsRepository {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}