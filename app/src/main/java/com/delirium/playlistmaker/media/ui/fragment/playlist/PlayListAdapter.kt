package com.delirium.playlistmaker.media.ui.fragment.playlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.media.domain.model.PlayListData

class PlayListAdapter(
    private val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: List<PlayListData> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlayListViewHolder(view, context)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlayListViewHolder).bind(data[position])
    }
}