package com.stackexchangelite.app.di

import com.stackexchangelite.app.di.main.MainFragmentBuildersModule
import com.stackexchangelite.app.di.main.MainModule
import com.stackexchangelite.app.di.main.MainScope
import com.stackexchangelite.app.di.main.MainViewModelsModule
import com.stackexchangelite.app.ui.activities.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @MainScope
    @ContributesAndroidInjector(modules = [MainFragmentBuildersModule::class, MainViewModelsModule::class, MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}