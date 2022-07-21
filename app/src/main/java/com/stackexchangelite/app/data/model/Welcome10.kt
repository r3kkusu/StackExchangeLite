package com.stackexchangelite.app.data.model

import com.google.gson.annotations.SerializedName

data class Welcome10 (
    @SerializedName("items")
    val items: List<Item>? = null,

    @SerializedName("has_more")
    val hasMore: Boolean? = null,

    @SerializedName("backoff")
    val backoff: Long? = null,

    @SerializedName("quota_max")
    val quotaMax: Long? = null,

    @SerializedName("quota_remaining")
    val quotaRemaining: Long? = null
)