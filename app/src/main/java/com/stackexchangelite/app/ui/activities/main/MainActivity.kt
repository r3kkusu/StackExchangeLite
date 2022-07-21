package com.stackexchangelite.app.ui.activities.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.stackexchangelite.app.R
import com.stackexchangelite.app.ui.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val TAG = "MainActivity"

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this, providerFactory)[MainViewModel::class.java]
        viewModel.getQuestionsData().observe(this) {

        }
        viewModel.pullQuestions(1)
    }
}