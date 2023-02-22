package com.delirium.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class SettingsActivity: AppCompatActivity() {

    private lateinit var sharingApp: TextView
    private lateinit var messageSupport: TextView
    private lateinit var termsUser: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolBar: Toolbar = findViewById(R.id.toolBarSetting)
        setSupportActionBar(toolBar)

        sharingApp = findViewById(R.id.sharing_app)
        messageSupport = findViewById(R.id.message_support)
        termsUser = findViewById(R.id.terms_user)

        sharingApp.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, LINK_TO_SITE)
            shareIntent.type = "text/plain"
            startActivity(shareIntent)
        }
        messageSupport.setOnClickListener {
            val supportIntent = Intent(Intent.ACTION_SENDTO)
            supportIntent.data = Uri.parse("mailto:")
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(DEVELOPER_MAIL))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT, THEME_TO_SUPPORT)
            supportIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE_TO_SUPPORT)
            startActivity(supportIntent)
        }
        termsUser.setOnClickListener {
            val address = Uri.parse("https://yandex.ru/legal/practicum_offer/")
            val termIntent = Intent(Intent.ACTION_VIEW, address)
            startActivity(termIntent)
        }
    }

    companion object {
        const val THEME_TO_SUPPORT = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
        const val MESSAGE_TO_SUPPORT = "Спасибо разработчикам и разработчицам за крутое приложение!"
        const val LINK_TO_SITE = "https://practicum.yandex.ru/profile/android-developer/"
        const val DEVELOPER_MAIL = "delirium.ag@yandex.ru"
    }
}