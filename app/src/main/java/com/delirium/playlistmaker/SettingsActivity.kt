package com.delirium.playlistmaker

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.Toolbar

class SettingsActivity: AppCompatActivity() {

    private lateinit var sharingApp: TextView
    private lateinit var messageSupport: TextView
    private lateinit var termsUser: TextView
    private lateinit var switch: SwitchCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolBar: Toolbar = findViewById(R.id.toolBarSetting)
        setSupportActionBar(toolBar)

        sharingApp = findViewById(R.id.sharing_app)
        messageSupport = findViewById(R.id.message_support)
        termsUser = findViewById(R.id.terms_user)
        switch = findViewById(R.id.switch_mode)

        when(resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                switch.isChecked = true
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                switch.isChecked = false
            }
        }
        switch.setOnCheckedChangeListener { compoundButton, isNight ->
            setModeTheme(isNight)
        }

        sharingApp.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.link_to_site))
            shareIntent.type = "text/plain"
            startActivity(shareIntent)
        }
        messageSupport.setOnClickListener {
            val supportIntent = Intent(Intent.ACTION_SENDTO)
            supportIntent.data = Uri.parse("mailto:")
            supportIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.developer_mail)))
            supportIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.theme_to_support))
            supportIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_to_support))
            startActivity(supportIntent)
        }
        termsUser.setOnClickListener {
            val address = Uri.parse(getString(R.string.user_term_link))
            val termIntent = Intent(Intent.ACTION_VIEW, address)
            startActivity(termIntent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setModeTheme(isNight: Boolean) {
        if(isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}