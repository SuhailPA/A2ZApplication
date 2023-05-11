package com.example.a2zapplication.repository.requestAccess

import com.example.a2zapplication.repository.requestAccess.firebaseDbAccess.FirebaseDbAuthenticator
import javax.inject.Inject

class FirestoreDbAccess @Inject constructor(private val firebaseDbAuthenticator: FirebaseDbAuthenticator): BaseDbAccess {
    override suspend fun setRequestForAccess(accessValue: HashMap<String,Boolean>) =
        firebaseDbAuthenticator.setRequestForAccess(accessValue)

}