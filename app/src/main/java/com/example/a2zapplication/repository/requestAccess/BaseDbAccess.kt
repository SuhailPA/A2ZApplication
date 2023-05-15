package com.example.a2zapplication.repository.requestAccess

import com.example.a2zapplication.data.model.firebase.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface BaseDbAccess {
    suspend fun setRequestForAccess(user : User) : Task<Void>?

    suspend fun fetchClassDetails() : Task<QuerySnapshot>
}