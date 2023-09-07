package com.delirium.playlistmaker.player.ui.fragment

import android.content.Context
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.player.domain.model.PlayListData
import java.io.File

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
        countSong.text = context.getString(R.string.playlist_count_song, data.countSong.toString())

        data.image?.let {
            val filePath = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "playlist_maker_image")
            val file = File(filePath, it)

            Glide.with(itemView)
                .load(file.toUri())
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
}