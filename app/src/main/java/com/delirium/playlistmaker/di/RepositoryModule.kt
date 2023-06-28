package com.delirium.playlistmaker.di

import com.delirium.playlistmaker.search.domain.api.RetrofitRepository
import com.delirium.playlistmaker.search.domain.impl.RetrofitRepositoryImpl
import org.koin.dsl.module

val repositorySearchModule = module {
    single<RetrofitRepository> {
        RetrofitRepositoryImpl(get())
    }
}
