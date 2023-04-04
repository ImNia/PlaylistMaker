package com.delirium.playlistmaker.search

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.search.itunes.model.*
import com.delirium.playlistmaker.search.songslist.ClickListener
import java.text.SimpleDateFormat
import java.util.*

class ViewHolderSongs(itemView: View, private val clickListener: ClickListener) : RecyclerView.ViewHolder(itemView) {
    private val imageSong: ImageView
    private val nameSong: TextView
    private val artistName: TextView
    private val timeSong: TextView

    init {
        imageSong = itemView.findViewById(R.id.image_song_item)
        nameSong = itemView.findViewById(R.id.name_song_item)
        artistName = itemView.findViewById(R.id.artist_name_item)
        timeSong = itemView.findViewById(R.id.time_song_item)
    }

    fun bind(data: SongItem) {
        Glide.with(itemView)
            .load(data.artworkUrl100)
            .placeholder(R.drawable.not_image)
            .transform(RoundedCorners(4))
            .into(imageSong)
        nameSong.text = data.trackName
        artistName.text = data.artistName
        timeSong.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(data.trackTimeMillis)

        itemView.setOnClickListener {
            clickListener.clickOnSong(data)
        }
    }
}

class ViewHolderSongsTitle(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textTitle: TextView

    init {
        textTitle = itemView.findViewById(R.id.item_song_title)
    }
    fun bind(data: SongItemTitle) {
        textTitle.text = data.text
    }
}

class ViewHolderSongsButton(itemView: View, private val clickListener: ClickListener)
    : RecyclerView.ViewHolder(itemView) {
    private val buttonClean: Button

    init {
        buttonClean = itemView.findViewById(R.id.item_song_clean)
    }
    fun bind(data: SongItemButton) {
        buttonClean.text = data.text
        buttonClean.setOnClickListener {
            clickListener.cleanHistory()
        }
    }
}

class ViewHolderSongsNotFound(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val image: ImageView
    private val text: TextView

    init {
        image = itemView.findViewById(R.id.image_problem_item)
        text = itemView.findViewById(R.id.text_problem_item)
    }

    fun bind(data: NotFoundItem) {
        image.setImageResource(data.res)
        text.text = data.textProblem
    }
}

class ViewHolderSongsError(itemView: View, private val clickListener: ClickListener) :
    RecyclerView.ViewHolder(itemView) {
    private val image: ImageView
    private val text: TextView
    private val textSub: TextView
    private val button: Button

    init {
        image = itemView.findViewById(R.id.image_problem_item)
        text = itemView.findViewById(R.id.text_problem_item)
        textSub = itemView.findViewById(R.id.text_problem_item_sub)
        button = itemView.findViewById(R.id.button_update_item)
    }

    fun bind(data: ErrorItem) {
        image.setImageResource(data.res)
        text.text = data.text
        textSub.text = data.textSub

        button.setOnClickListener {
            clickListener.clickUpdate()
        }
    }
}