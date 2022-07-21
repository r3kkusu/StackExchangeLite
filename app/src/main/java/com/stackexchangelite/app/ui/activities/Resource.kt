package com.stackexchangelite.app.ui.activities

import androidx.annotation.Nullable


class Resource<T>(
    val status: Status,
    @Nullable val data: T,
    @Nullable val message: String?
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(@Nullable data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, @Nullable data: T): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(@Nullable data: T): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}