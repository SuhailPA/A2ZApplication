package com.example.a2zapplication.data.model.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var uid: String,
    var name: String? = null,
    var emailId: String? = null,
    val standard: String? = null,
    var number: String? = null,
    var photoUrl: String? = null,
    var accessApproved : Boolean? = null
) : Parcelable
