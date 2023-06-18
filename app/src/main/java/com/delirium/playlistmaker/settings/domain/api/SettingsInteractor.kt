package com.delirium.playlistmaker.settings.domain.api

import com.delirium.playlistmaker.settings.data.models.ThemeSettings

interface SettingsInteractor {
    fun getThemeSettings(): ThemeSettings
    fun updateThemeSetting(settings: ThemeSettings)
}