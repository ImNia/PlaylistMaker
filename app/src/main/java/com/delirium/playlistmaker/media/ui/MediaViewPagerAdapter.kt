package com.delirium.playlistmaker.media.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.delirium.playlistmaker.media.ui.fragment.FavoriteTrackFragment
import com.delirium.playlistmaker.media.ui.fragment.PlayListMediaFragment
import java.lang.IllegalArgumentException

class MediaViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return COUNT_PAGE
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FavoriteTrackFragment.newInstance()
            1 -> PlayListMediaFragment.newInstance()
            else -> throw IllegalArgumentException()
        }
    }

    companion object {
        const val COUNT_PAGE = 2
    }
}