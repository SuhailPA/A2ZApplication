package com.example.a2zapplication.repository.login.googleAuthenticator

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.tasks.Task

interface BaseGoogleAuthenticator {

    fun googleSignInOneTap() : Task<BeginSignInResult>
}