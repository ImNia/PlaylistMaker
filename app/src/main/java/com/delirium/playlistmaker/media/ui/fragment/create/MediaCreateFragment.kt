package com.delirium.playlistmaker.media.ui.fragment.create

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentMediaCreateBinding
import com.delirium.playlistmaker.media.domain.model.PlayListData
import com.delirium.playlistmaker.media.ui.models.MediaCreateState
import com.delirium.playlistmaker.media.ui.models.PlaylistEditState
import com.delirium.playlistmaker.media.ui.viewmodel.MediaCreateViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MediaCreateFragment : Fragment() {
    private val viewModel by viewModel<MediaCreateViewModel>()
    private lateinit var binding: FragmentMediaCreateBinding

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    private var currentImageUri: Uri? = null

    lateinit var confirmDialog: MaterialAlertDialogBuilder
    private var playlistId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaCreateBinding.inflate(layoutInflater)
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                Glide.with(this)
                    .load(uri)
                    .placeholder(R.drawable.not_image)
                    .transform(
                        CenterCrop(),
                        RoundedCorners(
                            resources.getDimensionPixelSize(R.dimen.corner_description_image)
                        )
                    )
                    .into(binding.mediaCreateImage)
                currentImageUri = uri
            } else {
                Log.d("TEST", "No media selected")
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (playlistId == null) {
            playlistId = arguments?.getString(PLAYLIST_ID)
        }

        binding.mediaCreateImage.setOnClickListener {
            setImage()
        }

        binding.mediaCreateName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (binding.mediaCreateName.text.toString() == "") {
                    binding.mediaCreateButton.setBackgroundResource(R.drawable.create_button_not_active)
                    binding.mediaCreateButton.isEnabled = false
                } else {
                    binding.mediaCreateButton.setBackgroundResource(R.drawable.create_button_active)
                    binding.mediaCreateButton.isEnabled = true
                }
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        binding.mediaCreateButton.setOnClickListener {
            if (it.isEnabled) {
                viewModel.clickButtonCreate(
                    idPlaylist = playlistId?.toLong(),
                    name = binding.mediaCreateName.text.toString(),
                    description = binding.mediaCreateDescription.text.toString(),
                    uri = currentImageUri
                )
            }
        }

        if(playlistId == null) {
            createNewPlaylist()
        } else {
            editExistPlaylist()
        }
    }

    private fun createNewPlaylist() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                closeScreen()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(callback)
        binding.arrowBack.setOnClickListener {
            closeScreen()
        }

        confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.create_playlist_dialog_title))
            .setMessage(getString(R.string.create_playlist_dialog_message))
            .setNeutralButton(getString(R.string.create_playlist_dialog_neutral)) { dialog, which ->
            }.setNegativeButton(getString(R.string.create_playlist_dialog_close)) { dialog, which ->
                lifecycleScope.launch {
                    findNavController().popBackStack()
                }
            }

        viewModel.getMediaCreateStatePlayerLiveData().observe(viewLifecycleOwner) { state ->
            when(state) {
                is MediaCreateState.Created -> {
                    Toast.makeText(requireContext(), getString(R.string.playlist_created, state.name), Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    private fun editExistPlaylist() {
        binding.createPlaylistTitle.text = getString(R.string.edit_playlist_title)
        binding.mediaCreateButton.text = getString(R.string.edit_playlist_button)

        confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.edit_playlist_dialog_title))
            .setMessage(getString(R.string.create_playlist_dialog_message))
            .setNeutralButton(getString(R.string.create_playlist_dialog_neutral)) { dialog, which ->
            }.setNegativeButton(getString(R.string.create_playlist_dialog_close)) { dialog, which ->
                val bundle = Bundle()
                bundle.putString(PLAYLIST_ID, playlistId.toString())
                findNavController().navigate(R.id.mediaCreateFragment_back, bundle)
            }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.closeScreen(
                    binding.mediaCreateName.text.toString(),
                    binding.mediaCreateDescription.text.toString(),
                    currentImageUri
                )
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(callback)
        binding.arrowBack.setOnClickListener {
            viewModel.closeScreen(
                binding.mediaCreateName.text.toString(),
                binding.mediaCreateDescription.text.toString(),
                currentImageUri
            )
        }

        viewModel.getPlaylistStateLiveData().observe(viewLifecycleOwner) { playlistState ->
            when(playlistState) {
                is PlaylistEditState.Content -> {
                    updateScreenPlaylist(playlistState.playlist)
                    currentImageUri = playlistState.playlist.image?.toUri()
                }
                is PlaylistEditState.CloseScreen -> {
                    closeEditScreen(playlistState.updated)
                }
            }
        }
        viewModel.getMediaCreateStatePlayerLiveData().observe(viewLifecycleOwner) { state ->
            when(state) {
                is MediaCreateState.Created -> {
                    Toast.makeText(requireContext(), getString(R.string.playlist_updated, state.name), Toast.LENGTH_SHORT).show()
                    lifecycleScope.launch {
                        val bundle = Bundle()
                        bundle.putString(PLAYLIST_ID, playlistId.toString())
                        findNavController().navigate(R.id.mediaCreateFragment_back, bundle)
                    }
                }
            }
        }

        viewModel.initData(playlistId!!.toLong())
    }

    private fun updateScreenPlaylist(playlist: PlayListData) {
        binding.mediaCreateName.setText(playlist.name)
        binding.mediaCreateDescription.setText(playlist.description)
        Glide.with(this)
            .load(playlist.filePath)
            .placeholder(R.drawable.not_image)
            .transform(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_description_image)))
            .into(binding.mediaCreateImage)
    }

    private fun setImage() {
        pickMedia.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
    }
    private fun closeScreen() {
        if(binding.mediaCreateName.text.toString().trim() != ""
            || binding.mediaCreateDescription.text.toString().trim() != ""
            || currentImageUri != null) {
            confirmDialog.show()
        } else {
            lifecycleScope.launch {
                findNavController().popBackStack()
            }
        }
    }

    private fun closeEditScreen(updated: Boolean) {
        if(updated) {
            confirmDialog.show()
        } else {
            lifecycleScope.launch {
                val bundle = Bundle()
                bundle.putString(PLAYLIST_ID, playlistId.toString())
                findNavController().navigate(R.id.mediaCreateFragment_back, bundle)
            }
        }
    }

    companion object {
        const val PLAYLIST_ID = "PLAYLIST_ID"
    }
}