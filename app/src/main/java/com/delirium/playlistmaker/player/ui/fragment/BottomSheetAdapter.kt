package com.delirium.playlistmaker.player.ui.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.player.domain.model.PlayListData

class BottomSheetAdapter(
    private val context: Context,
    private val clickListener: ClickOnPlaylist
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: List<PlayListData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist_player, parent, false)
        return BottomSheetViewHolder(view, context, clickListener)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as BottomSheetViewHolder).bind(data[position])
    }
}

interface ClickOnPlaylist {
    fun clickOnPlaylist(playlist: PlayListData)
}