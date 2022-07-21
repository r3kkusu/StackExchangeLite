package com.stackexchangelite.app.data.model.enum

enum class UserType(val value: String) {
    Registered("registered");

    companion object {
        public fun fromValue(value: String): UserType = when (value) {
            "registered" -> Registered
            else         -> throw IllegalArgumentException()
        }
    }
}
