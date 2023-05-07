package com.example.a2zapplication.repository.login.firebaseAuthenticator

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions

interface BaseAuthenticator {

    suspend fun verifyPhoneNumber (options : PhoneAuthOptions)

    suspend fun getCredentials (verificationID : String, code : String) : PhoneAuthCredential

    suspend fun signInWithCredentials (phoneAuthCredential: PhoneAuthCredential): Task<AuthResult>
}