package com.delirium.playlistmaker.media.ui.fragment.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.media.domain.model.SongItemPlaylist

class PlayListAdapter(
    private val listener: ListenerSongPlaylist
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var data: List<SongItemPlaylist> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return PlayListViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PlayListViewHolder).bind(data[position], listener)
    }
}

interface ListenerSongPlaylist {
    fun clickOnSong(song: SongItemPlaylist)
    fun longClickOnSong(song: SongItemPlaylist)
}