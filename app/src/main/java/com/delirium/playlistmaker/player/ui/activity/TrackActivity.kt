package com.delirium.playlistmaker.player.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.databinding.ActivityDescriptionSongBinding
import com.delirium.playlistmaker.player.domain.model.TrackModel
import com.delirium.playlistmaker.player.ui.models.PlayerState
import com.delirium.playlistmaker.player.ui.models.TrackScreenState
import com.delirium.playlistmaker.player.ui.viewmodel.TrackViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class TrackActivity : AppCompatActivity() {
    private var trackId: String? = null
    private lateinit var binding: ActivityDescriptionSongBinding

    private val viewModel : TrackViewModel by inject { parametersOf(trackId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionSongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBarDescriptionSong)
        val bundle = intent.extras
        if (trackId == null) {
            trackId = bundle?.getString(TRACK_ID)
        }

        viewModel.getLoadingLiveData().observe(this) { isLoading ->
            changeProgressBarVisibility(isLoading)
        }

        viewModel.getScreenStateLiveData().observe(this) { screenState ->
            when (screenState) {
                is TrackScreenState.Content -> {
                    changeContentVisibility(loading = false)
                    updateScreen(screenState.trackModel)
                }

                is TrackScreenState.Loading -> {
                    changeContentVisibility(loading = true)
                }
            }
        }
        viewModel.getPlayStatusLiveData().observe(this) { playStatus ->
            when (playStatus.playerStatus) {
                PlayerState.STATE_PREPARED -> {
                    preparePlayer()
                    playbackControl(true)
                }

                PlayerState.STATE_PLAYING -> {
                    playbackControl(false)
                    binding.currentDurationSong.text = playStatus.progress
                }

                PlayerState.STATE_PAUSED -> {
                    playbackControl(true)
                }

                PlayerState.STATE_DEFAULT -> {

                }
            }
        }

        viewModel.preparePlayer()

        binding.playButtonDesc.setOnClickListener {
            viewModel.play()
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.closeScreen()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        trackId = savedInstanceState.getString(SAVE_TRACK)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVE_TRACK, trackId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun changeProgressBarVisibility(visible: Boolean) {
        binding.progressBar.isVisible = visible
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
    }

    private fun preparePlayer() {
        binding.currentDurationSong.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)
        binding.playButtonDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.play_button
            )
        )
    }

    private fun playbackControl(isPlaying: Boolean) {
        if (isPlaying) {
            pausePlayer()
        } else {
            startPlayer()
        }
    }

    private fun startPlayer() {
        binding.playButtonDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.pause_button
            )
        )
    }

    private fun pausePlayer() {
        binding.playButtonDesc.setImageDrawable(
            AppCompatResources.getDrawable(
                this,
                R.drawable.play_button
            )
        )
    }

    companion object {
        const val SAVE_TRACK = "SAVE_TRACK"
        const val TRACK_ID = "TRACK_ID"
    }
}