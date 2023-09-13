package com.delirium.playlistmaker.settings.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentSettingsBinding
import com.delirium.playlistmaker.sharing.model.ContentSharing
import com.delirium.playlistmaker.settings.model.StateSharing
import com.delirium.playlistmaker.settings.ui.viewmodel.SettingViewModel
import com.delirium.playlistmaker.sharing.model.EmailData
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel by viewModel<SettingViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getThemeSetting()

        viewModel.getThemeSettingLiveData().observe(viewLifecycleOwner) { isNight ->
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
}