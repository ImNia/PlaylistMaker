package com.delirium.playlistmaker.media.ui.fragment.playlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentPlaylistBinding
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.ui.models.PlaylistState
import com.delirium.playlistmaker.media.ui.models.SongPlaylistState
import com.delirium.playlistmaker.media.ui.viewmodel.PlaylistViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

class PlaylistFragment : Fragment() {
    private lateinit var binding: FragmentPlaylistBinding
    private val viewModel by viewModel<PlaylistViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initData(
            arguments?.getString(PLAYLIST_ID)
        )

        viewModel.getPlaylistStateLiveData().observe(viewLifecycleOwner) { playlistState ->
            when(playlistState) {
                is PlaylistState.Content -> {
                    updateScreen(playlistState.data)
                }
                is PlaylistState.Error -> {}
            }
        }

        viewModel.getSongsStateLiveData().observe(viewLifecycleOwner) { songState ->
            when(songState) {
                is SongPlaylistState.Content -> {
//                    Log.d("TEST", "${songState.data}")
                }
                SongPlaylistState.Empty -> {}
            }
        }
    }

    private fun updateScreen(playlist: PlayListData) {
        Log.d("TEST", Calendar.getInstance().get(Calendar.YEAR).toString())
        Log.d("TEST", "${playlist}")
        Glide.with(this)
            .load(playlist.filePath)
            .placeholder(R.drawable.not_image)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_description_image)))
            .into(binding.playlistImage)
        binding.playlistImage.scaleType = ImageView.ScaleType.FIT_CENTER
        binding.playlistName.text = playlist.name
        binding.playlistYear.text = playlist.year
        binding.playlistDuration.text = "???"
        binding.playlistCountTrack.text = playlist.countSong.toString()
    }

    companion object {
        private const val PLAYLIST_ID = "PLAYLIST_ID"
    }

}