package com.example.a2zapplication.repository.addUserDetails

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow


interface UserDetailsRepo {

    suspend fun fetchClassDetails() : Task<QuerySnapshot>
}