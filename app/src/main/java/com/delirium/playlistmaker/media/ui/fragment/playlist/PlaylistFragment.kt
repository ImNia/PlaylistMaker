package com.delirium.playlistmaker.media.ui.fragment.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentPlaylistBinding
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.domain.model.SongItemPlaylist
import com.delirium.playlistmaker.media.ui.models.PlaylistState
import com.delirium.playlistmaker.media.ui.models.ScreenState
import com.delirium.playlistmaker.media.ui.models.SongPlaylistState
import com.delirium.playlistmaker.media.ui.viewmodel.PlaylistViewModel
import com.delirium.playlistmaker.sharing.model.ContentSharing
import com.delirium.playlistmaker.sharing.model.EmailData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale

class PlaylistFragment : Fragment(), ListenerSongPlaylist {
    private lateinit var binding: FragmentPlaylistBinding
    private val viewModel by viewModel<PlaylistViewModel>()

    private val adapter by lazy {
        PlayListAdapter(this)
    }
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetBehaviorMenu: BottomSheetBehavior<LinearLayout>
    lateinit var confirmDialog: MaterialAlertDialogBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                closeScreen()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(callback)

        viewModel.initData(
            arguments?.getString(PLAYLIST_ID)
        )

        viewModel.getPlaylistStateLiveData().observe(viewLifecycleOwner) { playlistState ->
            when (playlistState) {
                is PlaylistState.Content -> {
                    updateScreen(playlistState.data)
                }

                is PlaylistState.Error -> {}
            }
        }

        viewModel.getSongsStateLiveData().observe(viewLifecycleOwner) { songState ->
            when (songState) {
                is SongPlaylistState.Content -> {
                    updateSongsInfo(songState.data)
                }

                SongPlaylistState.Empty -> {
                    updateSongsInfo(listOf())
                }
            }
        }

        viewModel.getScreenStateLiveData().observe(viewLifecycleOwner) { screenState ->
            when (screenState) {
                is ScreenState.NotSongs -> {
                    showMessage(getString(R.string.playlist_share_not_songs))
                    bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
                }

                is ScreenState.ShareSongs -> {
                    viewModel.sharing(
                        ContentSharing(
                            emailData = EmailData(
                                email = "",
                                themeMail = getString(R.string.playlist_share_theme),
                                messageOnMail = screenState.message
                            )
                        )
                    )
                    bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
                }

                is ScreenState.CloseScreen -> {
                    closeScreen()
                }

                is ScreenState.EditPlaylist -> {
                    val bundle = Bundle()
                    bundle.putString(PLAYLIST_ID, screenState.idPlaylist.toString())
                    findNavController().navigate(
                        R.id.action_playlistFragment_to_mediaCreateFragment,
                        bundle
                    )
                }
            }
        }
        binding.playlistArrowBack.setOnClickListener {
            closeScreen()
        }

        binding.playlistShareIcon.setOnClickListener {
            viewModel.clickOnSharedIcon()
        }

        binding.playlistMoreInfoIcon.setOnClickListener {
            openBottomSheetMenu()
        }

        binding.playlistRecyclerItem.layoutManager = LinearLayoutManager(requireContext())
        binding.playlistRecyclerItem.adapter = adapter

        bottomSheetBehavior = BottomSheetBehavior.from(binding.playlistBottomSheet)
        bottomSheetBehaviorMenu = BottomSheetBehavior.from(binding.playlistBottomSheetMenu)
        bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun updateScreen(playlist: PlayListData) {
        Glide.with(this)
            .load(playlist.filePath)
            .placeholder(R.drawable.not_image)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_description_image)))
            .into(binding.playlistImage)
        binding.playlistImage.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.playlistName.text = playlist.name
        binding.playlistCountTrack.text = getCorrectVersionTextNumberTrack(playlist.countSong)
        if(playlist.description != null && playlist.description != "") {
            binding.playlistDescription.visibility = View.VISIBLE
            binding.playlistDescription.text = playlist.description
        } else {
            binding.playlistDescription.visibility = View.GONE
        }

        Glide.with(this)
            .load(playlist.filePath)
            .placeholder(R.drawable.not_image)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_description_image)))
            .into(binding.playlistImageItemBottomSheet)
        binding.playlistNameItemBottomSheet.text = playlist.name
        binding.playlistCountItemBottomSheet.text = getCorrectVersionTextNumberTrack(playlist.countSong)
    }

    private fun updateSongsInfo(songs: List<SongItemPlaylist>) {
        val duration = duration(songs)
        binding.playlistDuration.text = if (duration >= 600000) {
            getCorrectVersionTextDuration(
                SimpleDateFormat("mm", Locale.getDefault()).format(duration(songs)).toLong()
            )
        } else {
            getCorrectVersionTextDuration(
                SimpleDateFormat("m", Locale.getDefault()).format(duration(songs)).toLong()
            )
        }
        adapter.data = songs
        adapter.notifyDataSetChanged()
    }

    private fun duration(songs: List<SongItemPlaylist>): Int {
        var duration = 0
        songs.forEach { song ->
            song.trackTimeMillis?.let {
                duration += it
            }
        }
        return duration
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun closeScreen() {
        lifecycleScope.launch {
            findNavController().popBackStack()
        }
    }

    override fun clickOnSong(song: SongItemPlaylist) {
        val bundle = Bundle()
        bundle.putString(TRACK_ID, song.trackId)
        findNavController().navigate(
            R.id.action_playlistFragment_to_trackFragment,
            bundle
        )
    }

    override fun longClickOnSong(song: SongItemPlaylist) {
        confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.playlist_question_delete_song))
            .setNegativeButton(getString(R.string.no)) { dialog, which -> }
            .setPositiveButton(R.string.yes) { _, _ ->
                viewModel.deleteSongFromPlaylist(song)
            }
        confirmDialog.show()
    }

    private fun openBottomSheetMenu() {
        bottomSheetBehaviorMenu.state = BottomSheetBehavior.STATE_HALF_EXPANDED

        binding.playlistSharingApp.setOnClickListener {
            viewModel.clickOnSharedIcon()
        }
        binding.playlistEditInfo.setOnClickListener {
            viewModel.openEditScreen()
        }
        binding.playlistDelete.setOnClickListener {
            confirmDialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.playlist_question_delete_playlist))
                .setNegativeButton(getString(R.string.no)) { dialog, which -> }
                .setPositiveButton(R.string.yes) { _, _ ->
                    viewModel.deletePlaylist()
                }
            confirmDialog.show()
        }
    }

    private fun getCorrectVersionTextNumberTrack(value: Long) = when(value % 10) {
        1L -> {
            getString(R.string.playlist_count_song_v2, value.toString())
        }
        2L, 3L, 4L -> {
            getString(R.string.playlist_count_song_v3, value.toString())
        }
        else -> {
            getString(R.string.playlist_count_song_v1, value.toString())
        }
    }

    private fun getCorrectVersionTextDuration(value: Long) = when(value % 10) {
        1L -> {
            getString(R.string.playlist_duration_song_v2, value.toString())
        }
        2L, 3L, 4L -> {
            getString(R.string.playlist_duration_song_v3, value.toString())
        }
        else -> {
            getString(R.string.playlist_duration_song_v1, value.toString())
        }
    }
    companion object {
        private const val PLAYLIST_ID = "PLAYLIST_ID"
        private const val TRACK_ID = "TRACK_ID"
    }
}