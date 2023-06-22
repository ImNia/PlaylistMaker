package com.delirium.playlistmaker.sharing.domain

import com.delirium.playlistmaker.settings.model.ContentSharing

interface ExternalNavigator {
    fun shareLink(content: ContentSharing)
    fun openLink(content: ContentSharing)
    fun openEmail(content: ContentSharing)
}