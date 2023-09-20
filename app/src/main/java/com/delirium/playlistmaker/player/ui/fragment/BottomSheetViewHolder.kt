package com.delirium.playlistmaker.player.ui.fragment

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.player.domain.model.PlayListData

class BottomSheetViewHolder(
    itemView: View,
    private val context: Context,
    private val clickListener: ClickOnPlaylist
): RecyclerView.ViewHolder(itemView) {
    private val image: ImageView
    private val name: TextView
    private val countSong: TextView

    init {
        image = itemView.findViewById(R.id.image_item_bottom_sheet)
        name = itemView.findViewById(R.id.name_item_bottom_sheet)
        countSong = itemView.findViewById(R.id.count_item_bottom_sheet)
    }

    fun bind(data: PlayListData) {
        name.text = data.name
        countSong.text = getCorrectVersionTextNumberTrack(data.countSong)

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
            clickListener.clickOnPlaylist(data)
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