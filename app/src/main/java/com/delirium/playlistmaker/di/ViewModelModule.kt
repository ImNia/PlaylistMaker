package com.delirium.playlistmaker.di

import com.delirium.playlistmaker.media.ui.viewmodel.MediaCreateViewModel
import com.delirium.playlistmaker.media.ui.viewmodel.FavoriteTrackViewModel
import com.delirium.playlistmaker.media.ui.viewmodel.MediaViewModel
import com.delirium.playlistmaker.media.ui.viewmodel.PlayListMediaViewModel
import com.delirium.playlistmaker.media.ui.viewmodel.PlaylistViewModel
import com.delirium.playlistmaker.player.ui.viewmodel.TrackViewModel
import com.delirium.playlistmaker.search.ui.viewmodel.SearchViewModel
import com.delirium.playlistmaker.settings.ui.viewmodel.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    /** Search
     * */
    viewModel {
        SearchViewModel(get(), get())
    }

    /** Player
     * */
    viewModel { params ->
        TrackViewModel(params.get(), get(), get(), get())
    }

    /** Setting
     * */
    viewModel {
        SettingViewModel(get(), get())
    }

    /** Media
     * */

    viewModel {
        MediaViewModel()
    }

    viewModel {
        PlayListMediaViewModel(get())
    }

    viewModel {
        FavoriteTrackViewModel(get())
    }

    viewModel {
        MediaCreateViewModel(get(), get())
    }
    viewModel {
        PlaylistViewModel(get(), get())
    }
}