package com.delirium.playlistmaker.settings.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.delirium.playlistmaker.databinding.ActivitySettingsBinding
import com.delirium.playlistmaker.settings.ui.viewmodel.SettingViewModel

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarSetting)

        viewModel = ViewModelProvider(
            this,
            SettingViewModel.getViewModelFactory()
        )[SettingViewModel::class.java]

        viewModel.getThemeSetting()

        viewModel.getThemeSettingLiveData().observe(this) { isNight ->
            changeSwitchTheme(isNight)
        }

        viewModel.getSharingIntentLiveData().observe(this) { intent ->
            startIntent(intent)
        }

        viewModel.getMessageToSupportLiveData().observe(this) { intent ->
            startIntent(intent)
        }

        viewModel.getOpenTermsUserLiveData().observe(this) { intent ->
            startIntent(intent)
        }

        binding.switchMode.setOnCheckedChangeListener { _, isNight ->
            viewModel.changeTheme(isNight)
        }

        binding.sharingApp.setOnClickListener {
            viewModel.sharingApp()
        }
        binding.messageSupport.setOnClickListener {
            viewModel.messageToSupport()
        }
        binding.termsUser.setOnClickListener {
            viewModel.openTermsUser()
        }
    }

    private fun startIntent(intent: Intent) {
        startActivity(intent)
    }

    private fun changeSwitchTheme(isNight: Boolean) {
        binding.switchMode.isChecked = isNight
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}