package com.delirium.playlistmaker.settings.ui.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.delirium.playlistmaker.App
import com.delirium.playlistmaker.settings.SingleLiveEvent
import com.delirium.playlistmaker.settings.data.models.ThemeSettings
import com.delirium.playlistmaker.settings.domain.api.SettingsInteractor
import com.delirium.playlistmaker.settings.domain.api.SharingInteractor

class SettingViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {
    private var themeSettingLiveData = MutableLiveData(true)
    fun getThemeSettingLiveData(): MutableLiveData<Boolean> = themeSettingLiveData

    private var sharingIntentLiveData = SingleLiveEvent<Intent>()
    fun getSharingIntentLiveData(): LiveData<Intent> = sharingIntentLiveData

    private var messageToSupportLiveData = SingleLiveEvent<Intent>()
    fun getMessageToSupportLiveData(): LiveData<Intent> = messageToSupportLiveData

    private var openTermsUserLiveData = SingleLiveEvent<Intent>()
    fun getOpenTermsUserLiveData(): LiveData<Intent> = openTermsUserLiveData

    fun sharingApp() {
        sharingIntentLiveData.value = sharingInteractor.shareApp()
    }

    fun messageToSupport() {
        messageToSupportLiveData.value = sharingInteractor.openSupport()
    }

    fun openTermsUser() {
        openTermsUserLiveData.value = sharingInteractor.openTerms()
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