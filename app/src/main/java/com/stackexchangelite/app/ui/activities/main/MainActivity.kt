package com.stackexchangelite.app.ui.activities.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.stackexchangelite.app.R
import com.stackexchangelite.app.data.model.Item
import com.stackexchangelite.app.ui.activities.Resource
import com.stackexchangelite.app.ui.adapter.QuestionsAdapter
import com.stackexchangelite.app.ui.adapter.QuestionsAdapterEvents
import com.stackexchangelite.app.ui.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    private val TAG = "MainActivity"

    @BindView(R.id.question_list)
    lateinit var recyclerQuestionList : RecyclerView

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: MainViewModel
    lateinit var questionsAdapter: QuestionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {

        questionsAdapter = QuestionsAdapter(object : QuestionsAdapterEvents {
            override fun onItemSelect(item: Item) {
            }
        }, requestManager)

        viewModel = ViewModelProvider(this, providerFactory)[MainViewModel::class.java]
        viewModel.getQuestionsData().observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    Log.d(TAG, "init: ${it.data.items}")
                    if (it.data.items != null) {
                        questionsAdapter.updateQuestionList(it.data.items)
                        questionsAdapter.notifyDataSetChanged()
                    }
                }
                Resource.Status.ERROR -> {

                }
            }
        }
        viewModel.pullQuestions(1)



        recyclerQuestionList.layoutManager = LinearLayoutManager(this)
        recyclerQuestionList.adapter = questionsAdapter
    }
}