package com.delirium.playlistmaker.media.ui.fragment.favorite

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.media.domain.model.SongItemFavorite
import java.text.SimpleDateFormat
import java.util.Locale

class ViewHolderFavorite(
    itemView: View,
    private val clickListener: ClickListenerFavorite
) : RecyclerView.ViewHolder(itemView) {
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

    fun bind(data: SongItemFavorite) {
        Glide.with(itemView)
            .load(data.artworkUrl100)
            .placeholder(R.drawable.not_image)
            .transform(
                CenterCrop(),
                RoundedCorners(
                    itemView.resources.getDimensionPixelSize(R.dimen.corner_song_image)
                )
            )
            .into(imageSong)
        nameSong.text = data.trackName
        artistName.text = data.artistName
        timeSong.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(data.trackTimeMillis)

        itemView.setOnClickListener {
            clickListener.clickOnSong(data)
        }
    }
}