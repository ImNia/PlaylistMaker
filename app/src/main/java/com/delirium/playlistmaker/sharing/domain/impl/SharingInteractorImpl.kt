package com.delirium.playlistmaker.sharing.domain.impl

import com.delirium.playlistmaker.sharing.model.ContentSharing
import com.delirium.playlistmaker.sharing.domain.ExternalNavigator
import com.delirium.playlistmaker.sharing.domain.SharingInteractor

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
): SharingInteractor {
    override fun shareApp(content: ContentSharing) {
        externalNavigator.shareLink(content)
    }

    override fun openTerms(content: ContentSharing) {
        externalNavigator.openLink(content)
    }

    override fun openSupport(content: ContentSharing) {
        externalNavigator.openEmail(content)
    }
}