package com.delirium.playlistmaker.settings.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.delirium.playlistmaker.App
import com.delirium.playlistmaker.settings.domain.model.ThemeSettings
import com.delirium.playlistmaker.settings.domain.api.SettingsInteractor
import com.delirium.playlistmaker.settings.model.ContentSharing
import com.delirium.playlistmaker.sharing.domain.SharingInteractor

class SettingViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {
    private var themeSettingLiveData = MutableLiveData(true)
    fun getThemeSettingLiveData(): MutableLiveData<Boolean> = themeSettingLiveData
    fun sharingApp(content: ContentSharing) {
        sharingInteractor.shareApp(content)
    }

    fun messageToSupport(content: ContentSharing) {
        sharingInteractor.openSupport(content)
    }

    fun openTermsUser(content: ContentSharing) {
        sharingInteractor.openTerms(content)
    }

    fun changeTheme(isLight: Boolean) {
        settingsInteractor.updateThemeSetting(
            ThemeSettings(
                isLight
            )
        )
        getThemeSetting()
    }

    fun getThemeSetting() {
        val theme = settingsInteractor.getThemeSettings()
        themeSettingLiveData.value = theme.isNight
    }

    companion object {
        fun getViewModelFactory() = viewModelFactory {
            initializer {
                val myApp = (this[APPLICATION_KEY] as App)
                val sharing = myApp.providerSharingInteractor()
                val settings = myApp.providerSettingsInteractor()
                SettingViewModel(sharing, settings)
            }
        }
    }
}