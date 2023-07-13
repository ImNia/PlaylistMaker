package com.delirium.playlistmaker.main.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentMainBinding
import com.delirium.playlistmaker.media.ui.fragment.MediaFragment
import com.delirium.playlistmaker.search.ui.activity.SearchFragment
import com.delirium.playlistmaker.settings.ui.fragment.SettingsFragment

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.findButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(
                    R.id.fragment_container_view,
                    SearchFragment.newInstance(),
                    SearchFragment.TAG
                )
                addToBackStack(SearchFragment.TAG)
            }
        }

        binding.mediaButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(
                    R.id.fragment_container_view,
                    MediaFragment.newInstance(),
                    MediaFragment.TAG
                )
                addToBackStack(MediaFragment.TAG)
            }
        }

        binding.settingButton.setOnClickListener {
            parentFragmentManager.commit {
                replace(
                    R.id.fragment_container_view,
                    SettingsFragment.newInstance(),
                    SettingsFragment.TAG
                )
                addToBackStack(SettingsFragment.TAG)
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}