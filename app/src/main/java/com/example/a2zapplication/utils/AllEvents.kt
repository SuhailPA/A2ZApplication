package com.example.a2zapplication.utils

sealed class AllEvents {
    data class Message(val message: Messages) : AllEvents()
    data class Error(val error: String) : AllEvents()
    data class AccessLevel(val accessType : AccessType) : AllEvents()

    data class Google(val error : GoogleError) : AllEvents()
}

enum class Messages{
    LOGGED_IN,
    OTP_DELIVERED,
    REQUEST_SENT
}

enum class GoogleError{
    ERROR
}
enum class AccessType{
    APPROVED,
    REJECTED
}