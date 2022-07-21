package com.stackexchangelite.app.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    companion object {

        val DATE_FORMAT_1 : String = "LLL dd, yyyy"
        val DATE_FORMAT_2 : String = "dd LLL yyyy 'at' h:mm a"

        @SuppressLint("SimpleDateFormat", "NewApi")
        fun format(epoch: Long, format: String): String {
            val sdf = SimpleDateFormat(format)
            val netDate = Date(epoch * 1000)
            return sdf.format(netDate)
        }
    }
}