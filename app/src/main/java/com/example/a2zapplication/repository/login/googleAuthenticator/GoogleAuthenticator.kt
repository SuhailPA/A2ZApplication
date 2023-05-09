package com.example.a2zapplication.repository.login.googleAuthenticator

import android.content.Context
import android.content.Intent
import com.example.a2zapplication.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class GoogleAuthenticator @Inject constructor(
    private val context: Context,
    private val oneTapClient: SignInClient
) : BaseGoogleAuthenticator {
    override suspend fun googleSignInOneTap(): Task<BeginSignInResult> {
        val signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(context.getString(R.string.server_client_id))
                .setFilterByAuthorizedAccounts(false).build()
        ).setAutoSelectEnabled(true).build()

        return oneTapClient.beginSignIn(signInRequest)
    }

    override suspend fun getGoogleTokenID(data: Intent) : Task<AuthResult> {
        val credential : SignInCredential = oneTapClient.getSignInCredentialFromIntent(data)
        val idToken : String? = credential.googleIdToken
        val userName = credential.id
        val password = credential.password
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken,null)

        return Firebase.auth.signInWithCredential(firebaseCredential)
    }
}