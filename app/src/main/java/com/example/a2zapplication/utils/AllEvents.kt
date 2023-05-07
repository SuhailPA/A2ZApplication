package com.example.a2zapplication.utils

sealed class AllEvents {
    data class Message(val message: Messages) : AllEvents()
    data class Error(val error: String) : AllEvents()
}

enum class Messages{
    LOGGED_IN,
    OTP_DELIVERED
}