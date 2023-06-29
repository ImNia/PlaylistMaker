package com.delirium.playlistmaker.di

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
        TrackViewModel(params.get(), get())
    }

    /** Setting
     * */
    viewModel {
        SettingViewModel(get(), get())
    }
}