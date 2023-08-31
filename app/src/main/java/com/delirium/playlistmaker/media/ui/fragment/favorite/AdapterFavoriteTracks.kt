package com.delirium.playlistmaker.media.ui.fragment.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.media.domain.model.EmptyFavorite
import com.delirium.playlistmaker.media.domain.model.ModelAdapterFavorite
import com.delirium.playlistmaker.media.domain.model.SongItemFavorite

class AdapterFavoriteTracks(
    private val clickListenerFavorite: ClickListenerFavorite
) : RecyclerView.Adapter<ViewHolder>() {
    var songs: List<ModelAdapterFavorite> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        SONG_TYPE -> {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
            ViewHolderFavorite(view, clickListenerFavorite)
        }

        EMPTY_TYPE -> {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite_empty, parent, false)
            ViewHolderEmptyFavorite(view)
        }

        else -> throw IllegalArgumentException()
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderFavorite -> {
                holder.bind(songs[position] as SongItemFavorite)
            }

            is ViewHolderEmptyFavorite -> {
                holder.bind(songs[position] as EmptyFavorite)
            }
        }
    }

    override fun getItemViewType(position: Int) = when (songs[position]) {
        is SongItemFavorite -> SONG_TYPE
        is EmptyFavorite -> EMPTY_TYPE
    }

    companion object {
        const val SONG_TYPE = 0
        const val EMPTY_TYPE = 1
    }
}

interface ClickListenerFavorite {
    fun clickOnSong(item: SongItemFavorite)
}