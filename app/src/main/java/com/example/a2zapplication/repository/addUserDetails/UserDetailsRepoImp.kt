package com.example.a2zapplication.repository.addUserDetails

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsRepoImp @Inject constructor(private val db : FirebaseFirestore): UserDetailsRepo {
    override suspend fun fetchClassDetails() = db.collection("availableClasses").get()

}