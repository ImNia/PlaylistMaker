package com.delirium.playlistmaker.settings.data

import android.content.Intent
import android.net.Uri
import com.delirium.playlistmaker.settings.models.EmailData

class ExternalNavigator {
    fun shareLink(appLink: String): Intent {
        return Intent(Intent.ACTION_VIEW).apply {
            putExtra(Intent.EXTRA_TEXT, appLink)
            type = "text/plain"
        }
    }

    fun openLink(termsLink: String): Intent {
        val address = Uri.parse(termsLink)
        return Intent(Intent.ACTION_VIEW, address)
    }

    fun openEmail(emailData: EmailData): Intent {
        return Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailData.email))
            putExtra(Intent.EXTRA_SUBJECT, emailData.themeMail)
            putExtra(Intent.EXTRA_TEXT, emailData.messageOnMail)
        }
    }
}