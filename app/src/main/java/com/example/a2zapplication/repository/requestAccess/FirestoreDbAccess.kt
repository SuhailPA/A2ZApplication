package com.example.a2zapplication.repository.requestAccess

import com.example.a2zapplication.data.model.firebase.User
import com.example.a2zapplication.repository.addUserDetails.UserDetailsRepo
import com.example.a2zapplication.repository.requestAccess.firebaseDbAccess.FirebaseDbAuthenticator
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class FirestoreDbAccess @Inject constructor(private val firebaseDbAuthenticator: FirebaseDbAuthenticator, private val userDetailsRepo: UserDetailsRepo): BaseDbAccess {
    override suspend fun setRequestForAccess(user : User) =
        firebaseDbAuthenticator.setRequestForAccess(user)

    override suspend fun fetchClassDetails() = userDetailsRepo.fetchClassDetails()



}