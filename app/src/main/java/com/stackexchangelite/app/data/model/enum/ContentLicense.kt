package com.stackexchangelite.app.data.model.enum

enum class ContentLicense(val value: String) {
    CcBySa40("CC BY-SA 4.0");

    companion object {
        public fun fromValue(value: String): ContentLicense = when (value) {
            "CC BY-SA 4.0" -> CcBySa40
            else           -> throw IllegalArgumentException()
        }
    }
}