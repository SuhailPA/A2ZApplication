package com.example.a2zapplication.data.roomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDetails(
    @PrimaryKey val uid : String,
    val name : String?,
    val phoneNumber : String?,
)
