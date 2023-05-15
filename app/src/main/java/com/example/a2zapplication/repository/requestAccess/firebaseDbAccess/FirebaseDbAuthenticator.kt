package com.example.a2zapplication.repository.requestAccess.firebaseDbAccess

import com.example.a2zapplication.data.model.firebase.User
import com.google.android.gms.tasks.Task

interface FirebaseDbAuthenticator {
    suspend fun setRequestForAccess(user: User) : Task<Void>?
}