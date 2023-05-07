package com.example.a2zapplication.repository.login.firebaseAuthenticator

import android.app.Activity
import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseAuthenticator : BaseAuthenticator {
    override suspend fun verifyPhoneNumber(options: PhoneAuthOptions) {
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun getCredentials(verificationID: String, code: String) =
        PhoneAuthProvider.getCredential(verificationID, code)

    override suspend fun signInWithCredentials(phoneAuthCredential: PhoneAuthCredential): Task<AuthResult> =
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
}