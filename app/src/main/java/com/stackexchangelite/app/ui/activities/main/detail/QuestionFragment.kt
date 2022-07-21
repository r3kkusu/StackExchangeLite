package com.stackexchangelite.app.ui.activities.main.detail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.RequestManager
import com.stackexchangelite.app.R
import com.stackexchangelite.app.data.model.Item
import com.stackexchangelite.app.ui.activities.UIFragmentWindowEvents
import com.stackexchangelite.app.utils.DateUtils
import dagger.android.support.DaggerFragment
import de.hdodenhof.circleimageview.CircleImageView

class QuestionFragment constructor(
    private val windowsListener: UIFragmentWindowEvents?,
    private val requestManager: RequestManager?,
    private val item: Item?
) : DaggerFragment() {

    @BindView(R.id.btn_close)
    lateinit var btnClose: ImageButton

    @BindView(R.id.txt_title)
    lateinit var txtTitle: TextView

    @BindView(R.id.txt_username)
    lateinit var txtUsername: TextView

    @BindView(R.id.txt_created_date)
    lateinit var txtCreatedDate: TextView

    @BindView(R.id.txt_activity_date)
    lateinit var txtActivityDate: TextView

    @BindView(R.id.img_user_avatar)
    lateinit var userAvatar: CircleImageView

    constructor() : this(null, null, null)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        windowsListener?.onWindowOpen()
    }

    override fun onDetach() {
        super.onDetach()
        windowsListener?.onWindowClosed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ButterKnife.bind(this, view)

        val animation: Animation = AnimationUtils.loadAnimation(activity, R.anim.fade_out)
        animation.duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

        // Fade out animation when user closes fragment
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(anim: Animation) {}
            override fun onAnimationRepeat(anim: Animation) {}
            override fun onAnimationEnd(anim: Animation) {
                try {
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.let {
                        it.remove(this@QuestionFragment )
                        it.commitAllowingStateLoss()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        btnClose.setOnClickListener {
            view.startAnimation(animation)
        }

        if (item != null) {
            txtTitle.text = item.title
            txtUsername.text = item.owner.displayName
            txtCreatedDate.text = "${getString(R.string.asked)}: ${DateUtils.format(item.creationDate, DateUtils.DATE_FORMAT_1)}"
            txtActivityDate.text = "${getString(R.string.last_active)}: ${DateUtils.format(item.lastActivityDate, DateUtils.DATE_FORMAT_2)}"
            requestManager?.load(item.owner.profileImage)?.into(userAvatar)
        }
    }
}