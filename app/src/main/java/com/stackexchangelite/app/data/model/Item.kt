package com.stackexchangelite.app.data.model

import com.google.gson.annotations.SerializedName
import com.stackexchangelite.app.data.model.enum.ContentLicense

data class Item (
    val tags: List<String>,
    val owner: Owner,

    @SerializedName("is_answered")
    val isAnswered: Boolean,

    @SerializedName("view_count")
    val viewCount: Long,

    @SerializedName("answer_count")
    val answerCount: Long,

    val score: Long,

    @SerializedName("last_activity_date")
    val lastActivityDate: Long,

    @SerializedName("creation_date")
    val creationDate: Long,

    @SerializedName("question_id")
    val questionID: Long,

    @SerializedName("content_license")
    val contentLicense: ContentLicense? = null,

    val link: String,
    val title: String,

    @SerializedName("last_edit_date")
    val lastEditDate: Long? = null,

    @SerializedName("accepted_answer_id")
    val acceptedAnswerID: Long? = null,

    @SerializedName("closed_date")
    val closedDate: Long? = null,

    @SerializedName("closed_reason")
    val closedReason: String? = null
)