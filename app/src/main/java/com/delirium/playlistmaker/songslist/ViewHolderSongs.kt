package com.delirium.playlistmaker.songslist

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.mock.Track

class ViewHolderSongs(itemView: View): RecyclerView.ViewHolder(itemView) {
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

    fun bind(data: Track) {
        Glide.with(itemView)
            .load(data.artworkUrl100)
            .placeholder(R.drawable.not_image)
            .transform(RoundedCorners(4))
            .into(imageSong)
        nameSong.text = data.trackName
        artistName.text = data.artistName
        timeSong.text = data.trackTime
    }
}