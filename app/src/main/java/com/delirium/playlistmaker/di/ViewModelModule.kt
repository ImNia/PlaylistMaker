package com.delirium.playlistmaker.di

import com.delirium.playlistmaker.player.ui.viewmodel.TrackViewModel
import com.delirium.playlistmaker.search.ui.viewmodel.SearchViewModel
import com.delirium.playlistmaker.settings.ui.viewmodel.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelSearchModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }
}

val viewModelPlayerModule = module {
    viewModel { params ->
        TrackViewModel(params.get(), get())
    }
}

val viewModelSettingModule = module {
    viewModel {
        SettingViewModel(get(), get())
    }
}