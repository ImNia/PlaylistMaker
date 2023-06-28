package com.delirium.playlistmaker.settings.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.ActivitySettingsBinding
import com.delirium.playlistmaker.settings.model.ContentSharing
import com.delirium.playlistmaker.settings.model.StateSharing
import com.delirium.playlistmaker.settings.ui.viewmodel.SettingViewModel
import com.delirium.playlistmaker.sharing.model.EmailData
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel by viewModel<SettingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarSetting)

        viewModel.getThemeSetting()

        viewModel.getThemeSettingLiveData().observe(this) { isNight ->
            changeSwitchTheme(isNight)
        }

        binding.switchMode.setOnCheckedChangeListener { _, isNight ->
            viewModel.changeTheme(isNight)
        }

        binding.sharingApp.setOnClickListener {
            viewModel.sharingApp(contentSharing(StateSharing.SHARING_APP))
        }
        binding.messageSupport.setOnClickListener {
            viewModel.messageToSupport(contentSharing(StateSharing.MESSAGE_TO_SUPPORT))
        }
        binding.termsUser.setOnClickListener {
            viewModel.openTermsUser(contentSharing(StateSharing.TERMS_USER))
        }
    }

    private fun contentSharing(content: StateSharing): ContentSharing {
        return when(content) {
            StateSharing.SHARING_APP -> {
                ContentSharing(
                    data = listOf(
                        Pair(Intent.EXTRA_TEXT, getString(R.string.link_to_site))
                    )
                )
            }
            StateSharing.MESSAGE_TO_SUPPORT -> {
                ContentSharing(
                    emailData = EmailData(
                        email = getString(R.string.developer_mail),
                        themeMail = getString(R.string.theme_to_support),
                        messageOnMail = getString(R.string.message_to_support)
                    )
                )
            }
            StateSharing.TERMS_USER -> {
                ContentSharing(
                    address = getString(R.string.user_term_link)
                )
            }
        }
    }

    private fun changeSwitchTheme(isNight: Boolean) {
        binding.switchMode.isChecked = isNight
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}