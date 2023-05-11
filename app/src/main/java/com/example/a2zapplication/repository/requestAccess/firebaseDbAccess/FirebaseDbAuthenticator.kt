package com.example.a2zapplication.repository.requestAccess.firebaseDbAccess

import com.google.android.gms.tasks.Task

interface FirebaseDbAuthenticator {
    suspend fun setRequestForAccess(accesValue : HashMap<String,Boolean>) : Task<Void>?
}