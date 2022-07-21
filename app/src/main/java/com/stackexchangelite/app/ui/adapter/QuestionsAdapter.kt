package com.stackexchangelite.app.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.stackexchangelite.app.R
import com.stackexchangelite.app.data.model.Item
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class QuestionsAdapter constructor(
    private val listener: QuestionsAdapterEvents,
    private val requestManager: RequestManager):
    RecyclerView.Adapter<QuestionsAdapter.QuestionHolder>() {

    val TAG = "QuestionAdapter"

    private var questionsList: List<Item> = listOf()

    class QuestionHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.item_avatar)
        lateinit var itemAvatar : CircleImageView

        @BindView(R.id.item_title)
        lateinit var itemTitle : TextView

        @BindView(R.id.item_date)
        lateinit var itemDate : TextView

        @BindView(R.id.item_root)
        lateinit var itemRoot : ConstraintLayout

        init {
            ButterKnife.bind(this, view);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        return QuestionHolder(inflate)
    }

    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
        val question = questionsList[position];

        requestManager.load(question.owner.profileImage).into(holder.itemAvatar)

        holder.itemTitle.text = question.title
        holder.itemDate.text = question.creationDate.toString()

        holder.itemRoot.setOnClickListener {
            listener.onItemSelect(question)
        }

    }

    override fun getItemCount(): Int {
        return questionsList.size
    }

    fun updateQuestionList(questions: List<Item>) {
        questionsList = questions
    }
}