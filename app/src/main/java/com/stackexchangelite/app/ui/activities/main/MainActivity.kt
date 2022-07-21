package com.stackexchangelite.app.ui.activities.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.stackexchangelite.app.R
import com.stackexchangelite.app.data.model.Item
import com.stackexchangelite.app.data.model.Welcome10
import com.stackexchangelite.app.ui.activities.Resource
import com.stackexchangelite.app.ui.activities.UIFragmentWindowEvents
import com.stackexchangelite.app.ui.activities.main.detail.QuestionFragment
import com.stackexchangelite.app.ui.adapter.QuestionsAdapter
import com.stackexchangelite.app.ui.adapter.QuestionsAdapterEvents
import com.stackexchangelite.app.ui.viewmodels.ViewModelProviderFactory
import com.stackexchangelite.app.utils.AnimationUtils
import com.stackexchangelite.app.utils.AppUtils
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(),
    View.OnClickListener,
    UIFragmentWindowEvents {

    private val TAG = "MainActivity"

    @BindView(R.id.question_list)
    lateinit var recyclerQuestionList : RecyclerView

    @BindView(R.id.layout_loading)
    lateinit var progressLayout: FrameLayout

    @BindView(R.id.layout_error)
    lateinit var errorLayout: FrameLayout

    @BindView(R.id.layout_dialog)
    lateinit var dialogLayout: FrameLayout

    @BindView(R.id.btn_prev)
    lateinit var btnPrev: TextView

    @BindView(R.id.btn_next)
    lateinit var btnNext: TextView

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var requestManager: RequestManager

    lateinit var viewModel: MainViewModel
    lateinit var questionsAdapter: QuestionsAdapter

    private var pageNumber: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        init()
    }

    private fun init() {
        questionsAdapter = QuestionsAdapter(object : QuestionsAdapterEvents {
            override fun onItemSelect(item: Item) {
                openQuestionFragment(item)
            }
        }, requestManager)

        viewModel = ViewModelProvider(this, providerFactory)[MainViewModel::class.java]
        viewModel.getQuestionsData().observe(this) {
            resetUIState()
            setUIState(it)
        }
        viewModel.pullQuestions(pageNumber)

        recyclerQuestionList.layoutManager = LinearLayoutManager(this)
        recyclerQuestionList.adapter = questionsAdapter

        btnPrev.setOnClickListener(this)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.btn_prev -> {
                if (pageNumber > 1) {
                    viewModel.pullQuestions(--pageNumber)
                }
            }
            R.id.btn_next -> { viewModel.pullQuestions(++pageNumber) }
        }
    }

    private fun resetUIState() {
        errorLayout.visibility = View.GONE
        progressLayout.visibility = View.GONE
        btnPrev.visibility = if (pageNumber <= 1) View.INVISIBLE else View.VISIBLE
        (recyclerQuestionList.layoutManager as LinearLayoutManager).scrollToPosition(0)
    }

    private fun setUIState(resource: Resource<Welcome10>) {
        when (resource.status) {
            Resource.Status.LOADING -> {
                Log.d(TAG, "setUIState: Fetching data...")
                progressLayout.visibility = View.VISIBLE
            }
            Resource.Status.SUCCESS -> {
                Log.d(TAG, "setUIState: Fetched data ${resource.data.items}")
                progressLayout.visibility = View.GONE
                if (resource.data.items != null) {
                    questionsAdapter.updateQuestionList(resource.data.items)
                    questionsAdapter.notifyDataSetChanged()
                }
            }
            Resource.Status.ERROR -> {
                Log.d(TAG, "setUIState: Failed to fetch data!")
                progressLayout.visibility = View.GONE
                errorLayout.visibility = View.VISIBLE
            }
        }
    }

    fun openQuestionFragment(item: Item) {
        if (dialogLayout.visibility != View.VISIBLE)
            AppUtils.replaceFragment(this, QuestionFragment(this, requestManager, item),
                AnimationUtils.ANIM_FADE_IN, R.id.layout_dialog)
    }

    override fun onWindowOpen() {
        Log.d(TAG, "onWindowOpen: ")
        dialogLayout.visibility = View.VISIBLE
    }

    override fun onWindowClosed() {
        Log.d(TAG, "onWindowClosed: ")
        dialogLayout.visibility =  View.GONE
    }
}