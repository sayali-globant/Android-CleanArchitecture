package com.example.marvels.di

import com.example.marvels.presentation.characters.CharactersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { CharactersViewModel(get()) }

    single { createGetPostsUseCase(get()) }

    single { createPostRepository(get()) }

    //   single { createProgressBar(androidContext()) }

}

