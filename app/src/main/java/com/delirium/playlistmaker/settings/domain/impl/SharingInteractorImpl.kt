package com.delirium.playlistmaker.settings.domain.impl

import android.content.Intent
import com.delirium.playlistmaker.settings.data.ExternalNavigator
import com.delirium.playlistmaker.settings.domain.api.SharingInteractor
import com.delirium.playlistmaker.settings.models.EmailData

class SharingInteractorImpl(
    private val externalNavigator: ExternalNavigator,
): SharingInteractor {
    override fun shareApp(): Intent {
        return externalNavigator.shareLink(getShareAppLink())
    }

    override fun openTerms(): Intent {
        return externalNavigator.openLink(getTermsLink())
    }

    override fun openSupport(): Intent {
        return externalNavigator.openEmail(getSupportEmailData())
    }

    private fun getShareAppLink(): String {
        return "https://practicum.yandex.ru/profile/android-developer/"
    }

    private fun getSupportEmailData(): EmailData {
        /*putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.developer_mail)))
        putExtra(Intent.EXTRA_SUBJECT, getString(R.string.theme_to_support))
        putExtra(Intent.EXTRA_TEXT, getString(R.string.message_to_support))*/
        return EmailData(
            email = "delirium.ag@yandex.ru",
            themeMail = "Сообщение разработчикам и разработчицам приложения Playlist Maker",
            messageOnMail = "Спасибо разработчикам и разработчицам за крутое приложение!"
        )
    }

    private fun getTermsLink(): String {
        //val address = Uri.parse(getString(R.string.user_term_link))
        return "https://yandex.ru/legal/practicum_offer/"
    }
}