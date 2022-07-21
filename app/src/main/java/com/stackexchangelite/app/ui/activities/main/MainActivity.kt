package com.stackexchangelite.app.ui.activities.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
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

    @BindView(R.id.layout_loading)
    lateinit var progressLayout: FrameLayout

    @BindView(R.id.layout_error)
    lateinit var errorLayout: FrameLayout

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
            errorLayout.visibility = View.GONE
            progressLayout.visibility = View.GONE
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.d(TAG, "init: Fetching data")
                    progressLayout.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    Log.d(TAG, "init: ${it.data.items}")
                    progressLayout.visibility = View.GONE
                    if (it.data.items != null) {
                        questionsAdapter.updateQuestionList(it.data.items)
                        questionsAdapter.notifyDataSetChanged()
                    }
                }
                Resource.Status.ERROR -> {
                    progressLayout.visibility = View.GONE
                    errorLayout.visibility = View.VISIBLE
                }
            }
        }
        viewModel.pullQuestions(1)



        recyclerQuestionList.layoutManager = LinearLayoutManager(this)
        recyclerQuestionList.adapter = questionsAdapter
    }
}