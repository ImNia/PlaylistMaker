package com.delirium.playlistmaker.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.SettingPreferences
import com.delirium.playlistmaker.search.model.SongItem
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
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

        val bundle = intent.extras
        if (trackId == null) {
            trackId = bundle?.getString(BundleKey.TRACK_ID.name)
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
            currentDurationSong.text = "0:30"
            durationSong.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(songItem?.trackTimeMillis)
            collectionSong.text = songItem?.collectionName

            val formatterData = SimpleDateFormat("yyyy")
            yearSong.text = songItem?.let {
                formatterData.format(formatterData.parse(it.releaseDate))
            }
            genreSong.text = songItem?.primaryGenreName
            countrySong.text = songItem?.country
        }
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        trackId = savedInstanceState.getString(TRACK_ID)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TRACK_ID, trackId)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
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
        const val TRACK_ID = "TRACK_ID"
    }
}