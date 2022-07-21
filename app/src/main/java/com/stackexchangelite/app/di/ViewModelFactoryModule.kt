package com.stackexchangelite.app.di

import androidx.lifecycle.ViewModelProvider
import com.stackexchangelite.app.ui.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}