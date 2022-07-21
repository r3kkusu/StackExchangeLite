package com.stackexchangelite.app.ui.activities.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stackexchangelite.app.data.network.MainApi
import com.stackexchangelite.app.ui.activities.Resource
import com.stackexchangelite.app.utils.Constants
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class MainViewModelTest : TestCase() {

    private val TAG = "MainViewModelTest"
    private lateinit var mainApi: MainApi

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    public override fun setUp() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mainApi = retrofit.create(MainApi::class.java)
    }

    @Test
    fun testResourceIsLoadingState() {
        val viewModel = MainViewModel(mainApi)

        viewModel.getQuestionsData().observeForever {
            assertTrue(it?.status == Resource.Status.LOADING)
        }
        viewModel.pullQuestions(1)
    }

    @Test
    fun testResourceIsSuccessState() {
        val viewModel = MainViewModel(mainApi)

        var skipFirstData = true
        viewModel.getQuestionsData().observeForever {
            if (skipFirstData) {
                assertTrue(it?.status == Resource.Status.SUCCESS)
            }
            skipFirstData = false
        }
        viewModel.pullQuestions(1)
    }

    @Test
    fun testResourceReturnsQuestionItemsInPageOne() {
        val viewModel = MainViewModel(mainApi)

        var skipFirstData = true
        viewModel.getQuestionsData().observeForever {
            if (skipFirstData && it.data.items != null) {
                assertTrue(it.data.items!!.isNotEmpty())
            }
            skipFirstData = false
        }
        viewModel.pullQuestions(1)
    }
}