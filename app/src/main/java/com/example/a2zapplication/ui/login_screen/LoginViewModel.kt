package com.example.a2zapplication.ui.login_screen

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a2zapplication.data.model.firebase.User
import com.example.a2zapplication.repository.login.BaseAuthRepository
import com.example.a2zapplication.utils.AccessType
import com.example.a2zapplication.utils.AllEvents
import com.example.a2zapplication.utils.GoogleError
import com.example.a2zapplication.utils.Messages
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: BaseAuthRepository,
    private val auth: FirebaseAuth
) : ViewModel() {


    lateinit var phoneNumber : String
    lateinit var userDetails: User
    lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var verificationID: String
    lateinit var token: PhoneAuthProvider.ForceResendingToken
    var allChannel = Channel<AllEvents>()
    val allEvents = allChannel.receiveAsFlow().asLiveData()
    var beginSignInResult = MutableLiveData<BeginSignInResult>()

    init {
        onVerificationCallbackMethod()
    }

    fun verifyPhoneNumber(options: PhoneAuthOptions) {
        viewModelScope.launch {
            repository.verifyPhoneNumber(options)
        }
    }

    private fun onVerificationCallbackMethod() {
        callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signInWithCredentials(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                viewModelScope.launch {
                    when (p0) {
                        is FirebaseAuthInvalidCredentialsException -> allChannel.send(
                            AllEvents.Error("It was an Invalid Request")
                        )

                        is FirebaseTooManyRequestsException ->{
                            allChannel.send(AllEvents.Error("The SMS Quota has been exceeded"))
                        }
                        is FirebaseAuthMissingActivityForRecaptchaException -> allChannel.send(
                            AllEvents.Error("reCaptcha verification attempted with null activity")
                        )
                        is FirebaseAuthException -> {
                           allChannel.send(AllEvents.Error("Verifying the app with Recaptcha failed, kindly complete the Recapcha process to continue login to the app"))
                        }
                    }
                }
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                viewModelScope.launch {
                    allChannel.send(AllEvents.Message(Messages.OTP_DELIVERED))
                    verificationID = p0
                    token = p1
                }
            }
        }
    }

    fun getCredentials(code: String) {
        viewModelScope.launch {
            signInWithCredentials(repository.getCredentials(verificationID, code))

        }
    }

    fun signInWithCredentials(phoneAuthCredential: PhoneAuthCredential) {
        viewModelScope.launch {
            repository.sigInWithCredentials(phoneAuthCredential).addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        userDetails = User(auth.currentUser?.uid.toString(), number = phoneNumber)
                        allChannel.send(AllEvents.Message(Messages.LOGGED_IN))
                    } else {
                        allChannel.send(AllEvents.Google(GoogleError.ERROR))
                    }
                }
            }

        }
    }

    fun beginSignInGoogleOneTap() {
        viewModelScope.launch {
            repository.googleSignOneTap().addOnSuccessListener {
                beginSignInResult.value = it
            }.addOnFailureListener {
                viewModelScope.launch {
                    allChannel.send(AllEvents.Google(GoogleError.ERROR))
                }
            }
        }
    }

    fun getIdFromGoogle(data: Intent) {
        viewModelScope.launch {
            repository.getGoogleID(data).addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        auth.currentUser?.let {
                            userDetails = User(
                                it.uid,
                                it.displayName,
                                it.email,
                                null,
                                null,
                                it.photoUrl.toString()
                            )
                        }
                        allChannel.send(AllEvents.Message(Messages.LOGGED_IN))
                    } else {
                        allChannel.send(AllEvents.Google(GoogleError.ERROR))
                    }
                }
            }
        }
    }

    fun checkAccessForUser() {
        viewModelScope.launch {
            repository.checkUserAccess()?.addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful && task.result.exists()) {
                        val accessType: Boolean = task.result.get("accessApproved") as Boolean
                        if (accessType) allChannel.send(AllEvents.AccessLevel(AccessType.APPROVED))
                        else allChannel.send(AllEvents.AccessLevel(AccessType.REJECTED))

                    } else {
                        allChannel.send(AllEvents.AccessLevel(AccessType.REJECTED))
                    }
                }
            }
        }

    }
}