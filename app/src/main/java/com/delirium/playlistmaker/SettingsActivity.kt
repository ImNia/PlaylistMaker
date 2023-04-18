package com.delirium.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharingAppButton: TextView
    private lateinit var messageSupportButton: TextView
    private lateinit var termsUserButton: TextView
    private lateinit var switchTheme: SwitchMaterial
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

        val sharedPrefs = getSharedPreferences(SettingPreferences.THEME_MODE.name, MODE_PRIVATE)
        switchTheme.isChecked = sharedPrefs.getBoolean(SettingPreferences.THEME_MODE.name, false)

        switchTheme.setOnCheckedChangeListener { switcher, isNight ->
            sharedPrefs.edit()
                .putBoolean(SettingPreferences.THEME_MODE.name, isNight)
                .apply()
            (applicationContext as App).switchTheme(
                sharedPrefs.getBoolean(
                    SettingPreferences.THEME_MODE.name,
                    false
                )
            )
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
}