package com.example.a2zapplication.utils

sealed class AllEvents {
    data class Message(val message: String) : AllEvents()
    data class Error(val error: String) : AllEvents()
}