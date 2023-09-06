package com.delirium.playlistmaker.media.ui.fragment.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delirium.playlistmaker.databinding.FragmentMediaCreateBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaCreateFragment : Fragment() {
    private val viewModel by viewModel<MediaCreateViewModel>()
    private lateinit var binding: FragmentMediaCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaCreateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}