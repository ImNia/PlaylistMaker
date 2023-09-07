package com.delirium.playlistmaker.media.ui.fragment.create

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.FragmentMediaCreateBinding
import com.delirium.playlistmaker.media.ui.models.MediaCreateState
import com.delirium.playlistmaker.media.ui.viewmodel.MediaCreateViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class MediaCreateFragment : Fragment() {
    private val viewModel by viewModel<MediaCreateViewModel>()
    private lateinit var binding: FragmentMediaCreateBinding

    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    private var currentImageUri: Uri? = null

    lateinit var confirmDialog: MaterialAlertDialogBuilder
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

        confirmDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.create_playlist_dialog_title))
            .setMessage(getString(R.string.create_playlist_dialog_message))
            .setNeutralButton(getString(R.string.create_playlist_dialog_neutral)) { dialog, which ->
            }.setNegativeButton(getString(R.string.create_playlist_dialog_close)) { dialog, which ->
                lifecycleScope.launch {
                    findNavController().popBackStack()
                }
            }

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

        binding.mediaCreateImage.setOnClickListener {
            setImage()
        }

        binding.arrowBack.setOnClickListener {
            closeScreen()
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
                var nameImage: String? = null
                currentImageUri?.let { imageUri ->
                    nameImage = (System.currentTimeMillis()/1000).toString()
                    saveImageToPrivateStorage(imageUri, nameImage!!)
                }
                viewModel.clickButtonCreate(
                    name = binding.mediaCreateName.text.toString(),
                    description = binding.mediaCreateDescription.text.toString(),
                    image = nameImage
                )
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

    private fun setImage() {
        pickMedia.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
        )
        binding.mediaCreateImage.scaleType = ImageView.ScaleType.CENTER_CROP
        binding.mediaCreateImage.setBackgroundResource(R.drawable.rounder_create_media)
    }

    private fun saveImageToPrivateStorage(uri: Uri, nameImage: String) {
        val filePath =
            File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), FILES_DIR)
        if (!filePath.exists()) {
            filePath.mkdirs()
        }
        val file = File(filePath, nameImage)
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
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

    companion object {
        const val FILES_DIR = "playlist_maker_image"
    }
}