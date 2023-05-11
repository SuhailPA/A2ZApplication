package com.example.a2zapplication.repository.requestAccess.firebaseDbAccess

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseDbAuthImp @Inject constructor(
    private val db : FirebaseFirestore,
    private val auth : FirebaseAuth
) : FirebaseDbAuthenticator {
    override suspend fun setRequestForAccess(accesValue: HashMap<String,Boolean>) = auth.currentUser?.uid?.let { db.collection("requestToAccess").document(it).set(accesValue)}

}