package com.example.a2zapplication.repository.login.googleAuthenticator

import android.content.Intent
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface BaseGoogleAuthenticator {

    suspend fun googleSignInOneTap() : Task<BeginSignInResult>

    suspend fun getGoogleTokenID(data : Intent) : Task<AuthResult>
}