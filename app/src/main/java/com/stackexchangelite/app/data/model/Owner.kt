package com.stackexchangelite.app.data.model

import com.google.gson.annotations.SerializedName
import com.stackexchangelite.app.data.model.enum.UserType

data class Owner (
    @SerializedName("account_id")
    val accountID: Long,

    val reputation: Long,

    @SerializedName("user_id")
    val userID: Long,

    @SerializedName("user_type")
    val userType: UserType,

    @SerializedName("accept_rate")
    val acceptRate: Long? = null,

    @SerializedName("profile_image")
    val profileImage: String,

    @SerializedName("display_name")
    val displayName: String,

    val link: String
)