package com.delirium.playlistmaker.settings.domain.impl

import com.delirium.playlistmaker.settings.data.SettingsRepository
import com.delirium.playlistmaker.settings.data.models.ThemeSettings
import com.delirium.playlistmaker.settings.domain.api.SettingsInteractor

class SettingsInteractorImpl(
    private val settingsRepository: SettingsRepository,
): SettingsInteractor {
    override fun getThemeSettings(): ThemeSettings {
        return settingsRepository.getThemeSettings()
    }

    override fun updateThemeSetting(settings: ThemeSettings) {
        return settingsRepository.updateThemeSetting(settings)
    }
}