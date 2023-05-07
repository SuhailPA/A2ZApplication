package com.example.a2zapplication.repository.login.googleAuthenticator

import android.content.Context
import com.example.a2zapplication.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class GoogleAuthenticator @Inject constructor(
    private val context: Context,
    private val oneTapClient: SignInClient
) : BaseGoogleAuthenticator {
    override fun googleSignInOneTap(): Task<BeginSignInResult> {
        val signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(context.getString(R.string.server_client_id))
                .setFilterByAuthorizedAccounts(false).build()
        ).setAutoSelectEnabled(true).build()

        return oneTapClient.beginSignIn(signInRequest)
    }
}