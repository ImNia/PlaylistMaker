package com.delirium.playlistmaker.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.delirium.playlistmaker.R
import com.delirium.playlistmaker.search.data.models.ModelForAdapter
import com.delirium.playlistmaker.search.data.models.ErrorItem
import com.delirium.playlistmaker.search.data.models.NotFoundItem
import com.delirium.playlistmaker.search.data.models.SongItem
import com.delirium.playlistmaker.search.data.models.SongItemButton
import com.delirium.playlistmaker.search.data.models.SongItemTitle

class AdapterModel(
    private val clickListener: ClickListener
) : RecyclerView.Adapter<ViewHolder>() {
    var songs: List<ModelForAdapter> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        SONG_TYPE -> {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
            ViewHolderSongs(view, clickListener)
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
        TITLE_TYPE -> {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false)
            ViewHolderSongsTitle(view)
        }
        BUTTON_TYPE -> {
            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_button_clean, parent, false)
            ViewHolderSongsButton(view, clickListener)
        }
        else -> throw IllegalArgumentException()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderSongs -> {
                if (songs[position] is SongItem) {
                    holder.bind(songs[position] as SongItem)
                }
            }
            is ViewHolderSongsNotFound -> {
                holder.bind(songs[position] as NotFoundItem)
            }
            is ViewHolderSongsError -> {
                holder.bind(songs[position] as ErrorItem)
            }
            is ViewHolderSongsTitle -> {
                holder.bind(songs[position] as SongItemTitle)
            }
            is ViewHolderSongsButton -> {
                holder.bind(songs[position] as SongItemButton)
            }
        }
    }

    override fun getItemCount() = songs.size

    override fun getItemViewType(position: Int) = when (songs[position]) {
        is SongItem -> SONG_TYPE
        is NotFoundItem -> NOT_FOUND_TYPE
        is ErrorItem -> ERROR_TYPE
        is SongItemTitle -> TITLE_TYPE
        is SongItemButton -> BUTTON_TYPE
        else -> throw IllegalArgumentException()
    }

    companion object {
        const val SONG_TYPE = 0
        const val NOT_FOUND_TYPE = 1
        const val ERROR_TYPE = 2
        const val TITLE_TYPE = 3
        const val BUTTON_TYPE = 4
    }
}

interface ClickListener {
    fun clickUpdate()
    fun clickOnSong(item: SongItem)
    fun cleanHistory()
}