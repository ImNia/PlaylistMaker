package com.delirium.playlistmaker.player.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentDescriptionSongBinding
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.domain.model.PlayListData
import com.delirium.playlistmaker.player.ui.models.PlayerState
import com.delirium.playlistmaker.player.ui.models.TrackScreenState
import com.delirium.playlistmaker.player.ui.viewmodel.TrackViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class TrackFragment : Fragment(), ClickOnPlaylist {
    private var trackId: String? = null
    private lateinit var binding: FragmentDescriptionSongBinding

    private val viewModel : TrackViewModel by inject { parametersOf(trackId) }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    private val adapter by lazy {
        BottomSheetAdapter(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDescriptionSongBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(callback)

        if (trackId == null) {
            trackId = arguments?.getString(TRACK_ID)
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                    }
                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

        viewModel.initViewModel()
        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is TrackScreenState.Content -> {
                    changeContentVisibility(loading = false)
                    viewModel.preparePlayer()
                    updateScreen(screenState.trackModel)
                }

                is TrackScreenState.Loading -> {
                    changeContentVisibility(loading = true)
                }

                is TrackScreenState.PlayerNotPrepared -> {
                    playerNotPrepared()
                }
                is TrackScreenState.BottomSheetShow -> {
                    openBottomSheet(screenState.data)
                }
            }
        }
        viewModel.getPlayerStateLiveData().observe(viewLifecycleOwner) { playerState ->
            when (playerState) {
                is PlayerState.Prepared -> {
                    preparePlayer(playerState.progress)
                }

                is PlayerState.Playing -> {
                    startPlayer()
                    binding.currentDurationSong.text = playerState.progress
                }

                is PlayerState.Paused -> {
                    pausePlayer()
                }

                is PlayerState.Default -> {
                    preparePlayer(playerState.progress)
                }
            }
        }

        binding.playButtonDesc.setOnClickListener {
            viewModel.clickButtonPlay()
        }

        binding.favoriteButtonDesc.setOnClickListener {
            viewModel.clickFavoriteButton()
        }

        binding.addedButtonDesc.setOnClickListener {
            viewModel.openBottomSheet()
        }

        binding.arrowBackPlayer.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonCreateNewPlaylistPlayer.setOnClickListener {
            findNavController().navigate(
                R.id.action_trackFragment_to_mediaCreateFragment
            )
        }

        binding.playlistRecyclerPlayer.layoutManager = LinearLayoutManager(requireContext())
        binding.playlistRecyclerPlayer.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateState()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

    }
    override fun onPause() {
        super.onPause()
        viewModel.pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.closeScreen()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVE_TRACK, trackId)
    }

    private fun changeContentVisibility(loading: Boolean) {
        binding.progressBar.isVisible = loading

        binding.imageDesc.isVisible = !loading
        binding.groupDesc.isVisible = !loading
        binding.nameSongDesc.isVisible = !loading
    }

    private fun updateScreen(track: TrackModel) {
        Glide.with(this)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.not_image)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_description_image)))
            .into(binding.imageDesc)
        binding.groupDesc.text = track.artistName
        binding.nameSongDesc.text = track.trackName
        binding.durationSong.text =
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis)
        binding.collectionSong.text = track.collectionName
        val formatterData = SimpleDateFormat("yyyy")
        binding.yearSong.text = track.let {
            formatterData.format(formatterData.parse(it.releaseDate))
        }
        binding.genreSong.text = track.primaryGenreName
        binding.countrySong.text = track.country
        binding.currentDurationSong.text = "00:00"

        if (track.isFavorite) {
            binding.favoriteButtonDesc.setImageResource(R.drawable.favorite_active)
        } else {
            binding.favoriteButtonDesc.setImageResource(R.drawable.favorite_desc)
        }
    }

    private fun preparePlayer(duration: String) {
        binding.currentDurationSong.text = duration
        binding.playButtonDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.play_button
            )
        )
    }

    private fun startPlayer() {
        binding.playButtonDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.pause_button
            )
        )
    }

    private fun pausePlayer() {
        binding.playButtonDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.drawable.play_button
            )
        )
    }

    private fun playerNotPrepared() {
        Toast.makeText(requireContext(), getString(R.string.player_not_prepared), Toast.LENGTH_SHORT).show()
    }

    private fun openBottomSheet(data: List<PlayListData>) {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        adapter.data = data
        adapter.notifyDataSetChanged()
    }

    companion object {
        const val SAVE_TRACK = "SAVE_TRACK"
        const val TRACK_ID = "TRACK_ID"
    }

    override fun clickOnPlaylist(playlist: PlayListData) {
        Log.d("TEST", "esrsf $trackId")
        trackId?.let { viewModel.addSongToPlaylist(it, playlist) }
    }
}