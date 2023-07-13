package com.delirium.playlistmaker.media.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.ActivityMediaBinding
import com.delirium.playlistmaker.media.ui.MediaViewPagerAdapter
import com.delirium.playlistmaker.media.ui.viewmodel.MediaViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaBinding
    private lateinit var tabMediator: TabLayoutMediator
    private val viewModel by viewModel<MediaViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMediaBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolBarSetting)

        binding.viewPager.adapter = MediaViewPagerAdapter(supportFragmentManager, lifecycle)
        tabMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.favorite_track)
                1 -> tab.text = getString(R.string.playlist)
            }
        }
        tabMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }
}