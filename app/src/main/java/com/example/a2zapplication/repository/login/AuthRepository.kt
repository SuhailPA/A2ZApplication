package com.example.a2zapplication.repository.login

import com.example.a2zapplication.repository.login.firebaseAuthenticator.BaseAuthenticator
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import javax.inject.Inject

class AuthRepository @Inject constructor(private val baseAuthenticator: BaseAuthenticator) :
    BaseAuthRepository {
    override suspend fun verifyPhoneNumber(options: PhoneAuthOptions) {
        baseAuthenticator.verifyPhoneNumber(options)
    }

    override suspend fun getCredentials(verificationID: String, code: String) =
        baseAuthenticator.getCredentials(verificationID, code)

    override suspend fun sigInWithCredentials(phoneAuthCredential: PhoneAuthCredential) =
        baseAuthenticator.signInWithCredentials(phoneAuthCredential)



}