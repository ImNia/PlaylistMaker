package com.delirium.playlistmaker.songslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.mock.Track

class AdapterSongs(
    private val songs: List<Track>
) : RecyclerView.Adapter<ViewHolderSongs> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSongs {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return ViewHolderSongs(view)
    }

    override fun onBindViewHolder(holder: ViewHolderSongs, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount() = songs.size
}
