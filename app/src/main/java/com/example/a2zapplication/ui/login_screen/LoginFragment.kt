package com.example.a2zapplication.ui.login_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.a2zapplication.R
import com.example.a2zapplication.databinding.FragmentLoginBinding
import com.example.a2zapplication.utils.AccessType
import com.example.a2zapplication.utils.AllEvents
import com.example.a2zapplication.utils.CustomAlertBox
import com.example.a2zapplication.utils.CustomProgressDialog
import com.example.a2zapplication.utils.GoogleError
import com.example.a2zapplication.utils.Messages
import com.example.a2zapplication.utils.Utils
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var launcher: ActivityResultLauncher<IntentSenderRequest>
    private var progressBar: CustomProgressDialog? = null
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
        launcher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult(),
            ::handleSignInResult
        )


        return binding?.root
    }

    private fun handleSignInResult(result: ActivityResult) {
        result.data?.let { viewModel.getIdFromGoogle(it) }
    }

    private fun initUI() {
        binding?.continueButton?.setOnClickListener {
            if (binding?.otpField?.isVisible == false) {
                val phoneNumber = binding?.mobileEmailEditText?.editText?.text.toString()
                if (phoneNumber.length < 10) binding?.mobileEmailEditText?.error =
                    getString(R.string.kindly_enter_the_valid_phone_number)
                else setPhoneAuthOptions("+91$phoneNumber")
            } else {
                val otp = binding?.otpField?.editText?.text.toString()
                if (otp.length < 6) binding?.otpField?.error = getString(R.string.invalid_otp)
                else {
                    showProgressBar("Verifying mobile number")
                    viewModel.getCredentials(otp)
                }
            }
        }

        binding?.googleIcon?.setOnClickListener {
            viewModel.beginSignInGoogleOneTap()
        }

        viewModel.beginSignInResult.observe(viewLifecycleOwner) {
            val intentSenderRequest = IntentSenderRequest.Builder(it.pendingIntent).build()
            launcher.launch(intentSenderRequest)
        }

        viewModel.allEvents.observe(viewLifecycleOwner) { event ->
            progressBar?.let {

            }
            if (progressBar?.isShowing() == true) progressBar?.dismiss()
            when (event) {
                is AllEvents.Message -> {
                    when (event.message) {
                        Messages.OTP_DELIVERED -> {
//                            progressBar.dismiss()
                            Toast.makeText(
                                context,
                                getString(R.string.otp_delivered),
                                Toast.LENGTH_LONG
                            ).show()
                            binding?.mobileEmailEditText?.visibility = View.GONE
                            binding?.otpField?.visibility = View.VISIBLE
                        }

                        Messages.LOGGED_IN -> {
                            progressBar?.dismiss()
                            Toast.makeText(
                                context,
                                getString(R.string.successfully_loggedin),
                                Toast.LENGTH_LONG
                            )
                                .show()
                            viewModel.checkAccessForUser()

                        }
                        else -> {}
                    }
                }

                is AllEvents.Error -> {
                    showAlertBox(event.error)
                }

                is AllEvents.AccessLevel -> {
                    when (event.accessType) {
                        AccessType.APPROVED -> {

                        }

                        else -> {
                            Toast.makeText(context, "Access Denied", Toast.LENGTH_LONG).show()
                            val action = viewModel.userDetails.let {
                                LoginFragmentDirections.actionLoginFragmentToRequestingAccessScreen2(
                                    it
                                )
                            }
                            binding?.root?.findNavController()?.navigate(action)
                        }
                    }
                }

                is AllEvents.Google -> {
                    when(event.error) {
                        GoogleError.ERROR -> {
                            showAlertBox("No Google Accounts found on this device, Kinldy SignIn to any google Account on this device")
                        }
                    }
                }
                else -> {}
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
            viewModel.phoneNumber = phoneNumber
            viewModel.verifyPhoneNumber(options)
        }
        showProgressBar("Sending OTP...")
    }

    private fun showProgressBar(message: String) {
        context?.let {
            progressBar = CustomProgressDialog(it)
            progressBar?.setText(message)
            progressBar?.showDialog()
        }
    }

    private fun showAlertBox(errorMsg: String) {
        val customAlertBox = context?.let { CustomAlertBox(it) }
            ?.setMessage(errorMsg)
            ?.setAlertBoxType(Utils.AlertBoxTypes.ERROR)
            ?.setTitle("Something went wrong")
            ?.setPositiveBtn(true, {})
            ?.setNegativeBtn(false, {})
            ?.setCancelable(false)
        customAlertBox?.show()
    }
}