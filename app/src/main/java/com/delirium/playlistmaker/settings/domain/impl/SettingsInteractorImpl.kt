package com.delirium.playlistmaker.settings.domain.impl

import com.delirium.playlistmaker.settings.domain.api.SettingsRepository
import com.delirium.playlistmaker.settings.domain.model.ThemeSettings
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