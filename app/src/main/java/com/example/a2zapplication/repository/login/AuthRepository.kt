package com.example.a2zapplication.repository.login

import android.content.Context
import com.example.a2zapplication.R
import com.example.a2zapplication.repository.login.firebaseAuthenticator.BaseAuthenticator
import com.example.a2zapplication.repository.login.googleAuthenticator.BaseGoogleAuthenticator
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val baseAuthenticator: BaseAuthenticator,
    private val googleAuthenticator : BaseGoogleAuthenticator
) : BaseAuthRepository {
    override suspend fun verifyPhoneNumber(options: PhoneAuthOptions) {
        baseAuthenticator.verifyPhoneNumber(options)
    }

    override suspend fun getCredentials(verificationID: String, code: String) =
        baseAuthenticator.getCredentials(verificationID, code)

    override suspend fun sigInWithCredentials(phoneAuthCredential: PhoneAuthCredential) =
        baseAuthenticator.signInWithCredentials(phoneAuthCredential)

    override suspend fun googleSignOneTap() : Task<BeginSignInResult> =
        googleAuthenticator.googleSignInOneTap()



}