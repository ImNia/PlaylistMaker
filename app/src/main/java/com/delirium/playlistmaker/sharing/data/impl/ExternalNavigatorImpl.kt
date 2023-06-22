package com.delirium.playlistmaker.sharing.data.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.delirium.playlistmaker.settings.model.ContentSharing
import com.delirium.playlistmaker.sharing.domain.ExternalNavigator

class ExternalNavigatorImpl(
    private val context: Context
): ExternalNavigator {
    override fun shareLink(content: ContentSharing) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            content.data?.forEach {
                putExtra(it.first, it.second)
            }
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            type = "text/plain"
        }
        context.startActivity(intent)
    }

    override fun openLink(content: ContentSharing) {
        Log.d("TEST", content.address ?: "!!!!")
        val address = Uri.parse(content.address)
        val intent = Intent(Intent.ACTION_VIEW, address)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    override fun openEmail(content: ContentSharing) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            content.emailData?.let {
                putExtra(Intent.EXTRA_EMAIL, it.email)
                putExtra(Intent.EXTRA_SUBJECT, it.themeMail)
                putExtra(Intent.EXTRA_TEXT, it.messageOnMail)
            }
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}