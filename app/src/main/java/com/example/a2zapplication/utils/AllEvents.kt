package com.example.a2zapplication.utils

sealed class AllEvents {
    data class Message(val message: Messages) : AllEvents()
    data class Error(val error: String) : AllEvents()
    data class AccessLevel(val accessType : AccessType) : AllEvents()

    data class FirebaseError(val error : String) : AllEvents()
}

enum class Messages{
    LOGGED_IN,
    OTP_DELIVERED
}

enum class AccessType{
    APPROVED,
    REJECTED
}