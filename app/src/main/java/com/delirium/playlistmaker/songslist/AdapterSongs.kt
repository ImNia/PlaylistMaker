package com.delirium.playlistmaker.songslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.searchitunes.model.*

class AdapterSongs(
    private val clickListener: ClickListener
) : RecyclerView.Adapter<ViewHolder>() {
    var songs: List<AdapterModel> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        SONG_TYPE -> {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
            ViewHolderSongs(view)
        }
        NOT_FOUND_TYPE -> {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_not_song, parent, false)
            ViewHolderSongsNotFound(view)
        }
        ERROR_TYPE -> {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_error_song, parent, false)
            ViewHolderSongsError(view, clickListener)
        }
        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ViewHolderSongs && songs[position] is SongItem) {
            holder.bind(songs[position] as SongItem)
        } else if (holder is ViewHolderSongsNotFound) {
            holder.bind(songs[position] as NotFoundItem)
        } else if (holder is ViewHolderSongsError) {
            holder.bind(songs[position] as ErrorItem)
        }
    }

    override fun getItemCount() = songs.size

    override fun getItemViewType(position: Int) = when (songs[position]) {
        is SongItem -> SONG_TYPE
        is NotFoundItem -> NOT_FOUND_TYPE
        is ErrorItem -> ERROR_TYPE
        else -> throw IllegalArgumentException()
    }

    companion object {
        const val SONG_TYPE = 0
        const val NOT_FOUND_TYPE = 1
        const val ERROR_TYPE = 1
    }
}

interface ClickListener {
    fun clickUpdate()
}