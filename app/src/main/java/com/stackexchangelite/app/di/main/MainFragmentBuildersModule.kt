package com.stackexchangelite.app.di.main

import com.stackexchangelite.app.ui.activities.main.detail.QuestionFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeQuestionFragment(): QuestionFragment
}