package com.example.a2zapplication.ui.login_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.a2zapplication.R
import com.example.a2zapplication.databinding.FragmentLoginBinding
import com.example.a2zapplication.utils.AllEvents
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        initUI()
        return binding?.root
    }

    private fun initUI() {
        binding?.continueButton?.setOnClickListener {
            if (binding?.otpField?.isVisible == false) {
                val phoneNumber = binding?.mobileEmailEditText?.editText?.text.toString()
                if (phoneNumber.length < 10) binding?.mobileEmailEditText?.error =
                    "Kindly enter the valid phone number"
                else setPhoneAuthOptions("+91$phoneNumber")
            } else {
                val otp = binding?.otpField?.editText?.text.toString()
                if (otp.length < 6) binding?.otpField?.error = "Invalid OTP"
                else viewModel.getCredentials(otp)
            }

        }

        viewModel.allEvents.observe(viewLifecycleOwner) { event ->
            when (event) {
                is AllEvents.Message -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                    binding?.mobileEmailEditText?.visibility = View.GONE
                    binding?.otpField?.visibility = View.VISIBLE
                }

                is AllEvents.Error -> {
                    binding?.mobileEmailEditText?.error = event.error
                }
            }
        }
    }

    /**
     * Create the PhoneAuthOptions Builder for verifying the phone number
     */
    private fun setPhoneAuthOptions(phoneNumber: String) {
        val options = activity?.let {
            PhoneAuthOptions.newBuilder(Firebase.auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(it)
                .setCallbacks(viewModel.callback)
                .build()
        }
        if (options != null) {
            viewModel.verifyPhoneNumber(options)
        }
    }

}