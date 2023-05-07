package com.example.a2zapplication.repository.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions

interface BaseAuthRepository {
    suspend fun verifyPhoneNumber(options: PhoneAuthOptions)

    suspend fun getCredentials(verificationID: String, code: String): PhoneAuthCredential

    suspend fun sigInWithCredentials(phoneAuthCredential: PhoneAuthCredential) : Task<AuthResult>
}