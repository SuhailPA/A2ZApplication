package com.example.a2zapplication.data.model.firebase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey var uid: String,
    var name: String? = null,
    var emailId: String? = null,
    val standard: String? = null,
    var number: String? = null,
    var photoUrl: String? = null,
    var accessApproved : Boolean? = null
) : Parcelable
