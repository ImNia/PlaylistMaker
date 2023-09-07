package com.delirium.playlistmaker.media.ui.fragment.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentFavoriteTrackBinding
import com.delirium.playlistmaker.media.domain.model.ModelAdapterFavorite
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

        viewModel.getOpenPlayerLiveData().observe(viewLifecycleOwner) { trackId ->
            openSongDescription(trackId)
        }

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

    override fun onResume() {
        super.onResume()
        viewModel.initData()
    }

    private fun updateScreen(data: List<ModelAdapterFavorite>) {
        adapter.songs = data
        adapter.notifyDataSetChanged()
    }

    private fun openSongDescription(trackId: String) {
        val bundle = Bundle()
        bundle.putString(TRACK_ID, trackId)
        findNavController().navigate(
            R.id.action_mediaFragment_to_trackFragment,
            bundle
        )
    }

    override fun clickOnSong(item: SongItemFavorite) {
        viewModel.openSongById(item)
    }

    companion object {
        fun newInstance() = FavoriteTrackFragment()
        private const val TRACK_ID = "TRACK_ID"
    }
}