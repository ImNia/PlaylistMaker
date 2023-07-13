package com.delirium.playlistmaker.media.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delirium.playlistmaker.databinding.FragmentPlayListMediaBinding
import com.delirium.playlistmaker.media.ui.viewmodel.PlayListMediaViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayListMediaFragment : Fragment() {

    private lateinit var binding: FragmentPlayListMediaBinding
    private val viewModel by viewModel<PlayListMediaViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayListMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = PlayListMediaFragment()
    }
}
