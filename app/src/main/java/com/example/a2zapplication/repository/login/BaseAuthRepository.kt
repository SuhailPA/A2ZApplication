package com.example.a2zapplication.repository.login

import android.content.Intent
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider

interface BaseAuthRepository {
    suspend fun verifyPhoneNumber(
        options: PhoneAuthOptions)

    suspend fun getCredentials(verificationID: String, code: String): PhoneAuthCredential

    suspend fun sigInWithCredentials(phoneAuthCredential: PhoneAuthCredential): Task<AuthResult>

    suspend fun googleSignOneTap(): Task<BeginSignInResult>

    suspend fun getGoogleID(data : Intent) : Task<AuthResult>
}