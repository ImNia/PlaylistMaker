package com.delirium.playlistmaker.settings.domain.api

import android.content.Intent

interface SharingInteractor {
    fun shareApp(): Intent
    fun openTerms(): Intent
    fun openSupport(): Intent
}