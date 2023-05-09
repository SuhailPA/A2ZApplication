package com.example.a2zapplication.repository.login.firebaseAuthenticator

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.firestore.DocumentSnapshot

interface BaseAuthenticator {

    suspend fun verifyPhoneNumber (options : PhoneAuthOptions)

    suspend fun getCredentials (verificationID : String, code : String) : PhoneAuthCredential

    suspend fun signInWithCredentials (phoneAuthCredential: PhoneAuthCredential): Task<AuthResult>

    suspend fun checkUserAccess() : Task<DocumentSnapshot>?
}