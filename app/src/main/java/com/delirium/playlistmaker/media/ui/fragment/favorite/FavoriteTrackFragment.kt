package com.delirium.playlistmaker.media.ui.fragment.favorite

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.delirium.playlistmaker.databinding.FragmentFavoriteTrackBinding
import com.delirium.playlistmaker.media.domain.model.SongItemFavorite
import com.delirium.playlistmaker.media.ui.viewmodel.FavoriteTrackViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTrackFragment : Fragment(), ClickListenerFavorite {

    private lateinit var binding: FragmentFavoriteTrackBinding
    private val viewModel by viewModel<FavoriteTrackViewModel>()

    private val adapter = AdapterFavoriteTracks(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteTrackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.initData()

        viewModel.getFavoriteLiveData().observe(viewLifecycleOwner) { screenState ->
            when(screenState) {
                is FavoriteStateScreen.Content -> {
                    updateScreen(screenState.data)
                }
                FavoriteStateScreen.NotFavorite -> {

                }
            }
        }

        binding.recyclerSongsFavorite.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerSongsFavorite.adapter = adapter
    }

    private fun updateScreen(data: List<SongItemFavorite>) {
        adapter.songs = data
        adapter.notifyDataSetChanged()
        if (data.isNotEmpty()) {
            binding.imageProblemItem.visibility = View.INVISIBLE
            binding.textProblemItem.visibility = View.INVISIBLE

        } else  {
            binding.imageProblemItem.visibility = View.VISIBLE
            binding.textProblemItem.visibility = View.VISIBLE
        }
    }

    override fun clickOnSong(item: SongItemFavorite) {
        TODO("Not yet implemented")
    }

    companion object {
        fun newInstance() = FavoriteTrackFragment()
    }
}