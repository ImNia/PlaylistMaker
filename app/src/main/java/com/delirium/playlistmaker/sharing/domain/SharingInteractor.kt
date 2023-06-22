package com.delirium.playlistmaker.sharing.domain

import com.delirium.playlistmaker.settings.model.ContentSharing

interface SharingInteractor {
    fun shareApp(content: ContentSharing)
    fun openTerms(content: ContentSharing)
    fun openSupport(content: ContentSharing)
}