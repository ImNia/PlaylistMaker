package com.delirium.playlistmaker.media.ui.fragment.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.media.domain.model.SongItemFavorite

class AdapterFavoriteTracks(
    private val clickListenerFavorite: ClickListenerFavorite
) : RecyclerView.Adapter<ViewHolder>() {
    var songs: List<SongItemFavorite> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return ViewHolderFavorite(view, clickListenerFavorite)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderFavorite).bind(songs[position])
    }
}

interface ClickListenerFavorite {
    fun clickOnSong(item: SongItemFavorite)
}