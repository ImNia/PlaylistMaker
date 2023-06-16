package com.delirium.playlistmaker.presentation

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.domain.models.SongItem
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class DescriptionSongActivity : AppCompatActivity() {
    private var trackId: String? = null
    private var songItem: SongItem? = null
    private lateinit var imageDesc: ImageView
    private lateinit var nameSong: TextView
    private lateinit var groupDesc: TextView
    private lateinit var currentDurationSong: TextView
    private lateinit var durationSong: TextView
    private lateinit var collectionSong: TextView
    private lateinit var yearSong: TextView
    private lateinit var genreSong: TextView
    private lateinit var countrySong: TextView
    private lateinit var playButton: ImageView

    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT
    private var mainThreadHandler: Handler? = null

    private var runnable = updateTimer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_song)

        val toolbar = findViewById<Toolbar>(R.id.toolBarDescriptionSong)
        setSupportActionBar(toolbar)

        imageDesc = findViewById(R.id.image_desc)
        nameSong = findViewById(R.id.name_song_desc)
        groupDesc = findViewById(R.id.group_desc)
        currentDurationSong = findViewById(R.id.current_duration_song)
        durationSong = findViewById(R.id.duration_song)
        collectionSong = findViewById(R.id.collection_song)
        yearSong = findViewById(R.id.year_song)
        genreSong = findViewById(R.id.genre_song)
        countrySong = findViewById(R.id.country_song)
        playButton = findViewById(R.id.play_button_desc)

        val bundle = intent.extras
        if (trackId == null) {
            trackId = bundle?.getString(TRACK_ID)
        }

        if (trackId != null) {
            songItem = getSong(trackId!!)

            Glide.with(this)
                .load(songItem?.artworkUrl100?.let { getCoverArtwork(it) })
                .placeholder(R.drawable.not_image)
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_description_image)))
                .into(imageDesc)

            nameSong.text = songItem?.trackName
            groupDesc.text = songItem?.artistName
            durationSong.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(songItem?.trackTimeMillis)
            collectionSong.text = songItem?.collectionName

            val formatterData = SimpleDateFormat("yyyy")
            yearSong.text = songItem?.let {
                formatterData.format(formatterData.parse(it.releaseDate))
            }
            genreSong.text = songItem?.primaryGenreName
            countrySong.text = songItem?.country
        }

        mainThreadHandler = Handler(Looper.getMainLooper())

        preparePlayer(songItem?.previewUrl.orEmpty())
        playButton.setOnClickListener {
            playbackControl()
        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        mainThreadHandler?.removeCallbacks(runnable)
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

    private fun preparePlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
            currentDurationSong.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)
        }
        mediaPlayer.setOnCompletionListener {
            mainThreadHandler?.removeCallbacks(runnable)
            playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.play_button))
            currentDurationSong.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(0)
            playerState = STATE_PREPARED
            mediaPlayer.seekTo(0)
        }
    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
                startTimer()
            }
        }
    }
    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.pause_button))
        playerState = STATE_PLAYING
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.play_button))
        playerState = STATE_PAUSED
        mainThreadHandler?.removeCallbacks(runnable)
    }

    private fun startTimer() {
        mainThreadHandler?.post(runnable)
    }
    private fun updateTimer() : Runnable {
        return object : Runnable {
            override fun run() {
                if (playerState == STATE_PLAYING) {
                    val currentTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
                    currentDurationSong.text = currentTime
                    mainThreadHandler?.postDelayed(this, DELAY)
                }
            }
        }
    }

    private fun getSong(trackId: String): SongItem? {
        val sharedPrefs = getSharedPreferences(SettingPreferences.FINDING_SONG.name, MODE_PRIVATE)
        val jsonHistory =
            sharedPrefs.getString(SettingPreferences.FINDING_SONG.name, null)
        val allSong = Gson().fromJson(jsonHistory, Array<SongItem>::class.java)
        for (item in allSong) {
            if (item.trackId == trackId) {
                return item
            }
        }
        return null
    }

    private fun getCoverArtwork(artworkUrl: String) = artworkUrl.replaceAfterLast('/',"512x512bb.jpg")

    companion object {
        const val SAVE_TRACK = "SAVE_TRACK"
        const val TRACK_ID = "TRACK_ID"
        private const val DELAY = 1000L

        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
    }
}