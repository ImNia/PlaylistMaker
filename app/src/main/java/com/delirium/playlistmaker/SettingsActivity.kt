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

    private lateinit var sharingAppButton: TextView
    private lateinit var messageSupportButton: TextView
    private lateinit var termsUserButton: TextView
    private lateinit var switchTheme: SwitchCompat
    private lateinit var toolBar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        toolBar = findViewById(R.id.toolBarSetting)
        setSupportActionBar(toolBar)

        sharingAppButton = findViewById(R.id.sharing_app)
        messageSupportButton = findViewById(R.id.message_support)
        termsUserButton = findViewById(R.id.terms_user)
        switchTheme = findViewById(R.id.switch_mode)

        when(resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                switchTheme.isChecked = true
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                switchTheme.isChecked = false
            }
        }
        switchTheme.setOnCheckedChangeListener { compoundButton, isNight ->
            setModeTheme(isNight)
        }

        sharingAppButton.setOnClickListener {
            Intent(Intent.ACTION_VIEW).apply {
                putExtra(Intent.EXTRA_TEXT, getString(R.string.link_to_site))
                type = "text/plain"
                startActivity(this)
            }
        }
        messageSupportButton.setOnClickListener {
            Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.developer_mail)))
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.theme_to_support))
                putExtra(Intent.EXTRA_TEXT, getString(R.string.message_to_support))
                startActivity(this)
            }
        }
        termsUserButton.setOnClickListener {
            val address = Uri.parse(getString(R.string.user_term_link))
            Intent(Intent.ACTION_VIEW, address).apply {
                startActivity(this)
            }
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