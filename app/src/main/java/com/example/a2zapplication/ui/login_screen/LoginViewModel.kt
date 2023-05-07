package com.example.a2zapplication.ui.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.a2zapplication.repository.login.BaseAuthRepository
import com.example.a2zapplication.utils.AllEvents
import com.example.a2zapplication.utils.Messages
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: BaseAuthRepository
) : ViewModel() {


    lateinit var callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var verificationID: String
    lateinit var token: PhoneAuthProvider.ForceResendingToken
    var allChannel = Channel<AllEvents>()
    val allEvents = allChannel.receiveAsFlow().asLiveData()
    lateinit var phoneAuthCredentials: PhoneAuthCredential

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

                        is FirebaseTooManyRequestsException -> allChannel.send(AllEvents.Error("The SMS Quota has been exceeded"))
                        is FirebaseAuthMissingActivityForRecaptchaException -> allChannel.send(
                            AllEvents.Error("reCaptcha verification attempted with null activity")
                        )
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

    fun signInWithCredentials (phoneAuthCredential: PhoneAuthCredential) {
        viewModelScope.launch {
            repository.sigInWithCredentials(phoneAuthCredential).addOnCompleteListener {task ->
                viewModelScope.launch {
                    if (task.isSuccessful){
                        allChannel.send(AllEvents.Message(Messages.LOGGED_IN))
                    } else {
                        allChannel.send(AllEvents.Error(task.exception?.message.orEmpty()))
                    }
                }
            }

        }

    }
}