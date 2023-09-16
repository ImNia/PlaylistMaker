package com.delirium.playlistmaker.media.ui.fragment.media

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.media.domain.model.PlayListData

class PlayListMediaViewHolder(
    itemView: View,
    private val context: Context
): RecyclerView.ViewHolder(itemView) {
    private val image: ImageView
    private val name: TextView
    private val count: TextView

    init {
        image = itemView.findViewById(R.id.playlist_image_item)
        name = itemView.findViewById(R.id.playlist_name_item)
        count = itemView.findViewById(R.id.playlist_count_item)
    }

    fun bind(data: PlayListData, listener: ClickListenerPlaylist) {
        name.text = data.name
        count.text = getCorrectVersionTextNumberTrack(data.countSong)

        data.image?.let {
            Glide.with(itemView)
                .load(data.filePath)
                .placeholder(R.drawable.not_image)
                .transform(
                    CenterCrop(),
                    RoundedCorners(
                        itemView.resources.getDimensionPixelSize(R.dimen.corner_description_image)
                    )
                )
                .into(image)
        }
        itemView.setOnClickListener {
            listener.clickOnPlaylist(data.id)
        }
    }

    private fun getCorrectVersionTextNumberTrack(value: Long) = when(value % 10) {
        1L -> {
            context.getString(R.string.playlist_count_song_v2, value.toString())
        }
        2L, 3L, 4L -> {
            context.getString(R.string.playlist_count_song_v3, value.toString())
        }
        else -> {
            context.getString(R.string.playlist_count_song_v1, value.toString())
        }
    }
}