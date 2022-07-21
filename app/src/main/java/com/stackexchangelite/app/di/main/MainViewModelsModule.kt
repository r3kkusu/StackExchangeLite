package com.stackexchangelite.app.di.main

import androidx.lifecycle.ViewModel
import com.stackexchangelite.app.di.ViewModelKey
import com.stackexchangelite.app.ui.activities.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}