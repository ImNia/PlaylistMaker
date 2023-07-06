package com.delirium.playlistmaker.media.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.media.ui.viewmodel.PlayListMediaViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListMediaFragment : Fragment() {

    companion object {
        fun newInstance() = PlayListMediaFragment()
    }

    private val viewModel by viewModel<PlayListMediaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_play_list_media, container, false)
    }
}