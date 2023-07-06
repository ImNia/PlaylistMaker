package com.delirium.playlistmaker.media.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delirium.playlistmaker.databinding.FragmentFavoriteTrackBinding
import com.delirium.playlistmaker.media.ui.viewmodel.FavoriteTrackViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTrackFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteTrackBinding
    private val viewModel by viewModel<FavoriteTrackViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = FavoriteTrackFragment()
    }
}