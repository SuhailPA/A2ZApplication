package com.example.a2zapplication.repository.requestAccess.firebaseDbAccess

import com.example.a2zapplication.data.model.firebase.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseDbAuthImp @Inject constructor(
    private val db : FirebaseFirestore,
    private val auth : FirebaseAuth
) : FirebaseDbAuthenticator {
    override suspend fun setRequestForAccess(user : User) = auth.currentUser?.uid?.let { db.collection("requestToAccess").document(it).set(user)}

}