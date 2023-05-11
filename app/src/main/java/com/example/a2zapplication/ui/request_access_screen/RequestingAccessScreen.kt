package com.example.a2zapplication.ui.request_access_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.a2zapplication.R
import com.example.a2zapplication.databinding.FragmentRequestingAccessScreenBinding
import com.example.a2zapplication.utils.AllEvents
import com.example.a2zapplication.utils.Messages
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RequestingAccessScreen : Fragment() {

    private val viewModel : RequestAccessViewModel by viewModels()
    private lateinit var binding : FragmentRequestingAccessScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRequestingAccessScreenBinding.inflate(inflater,container,false)

        binding.closeButton.setOnClickListener {
            activity?.finish()
        }

        binding.requestAccessBtn.setOnClickListener {
            viewModel.setTheRequestForAccess()
        }

        viewModel.allEvents.observe(viewLifecycleOwner, Observer {
            when(it){
                is AllEvents.Message -> {
                    when(it.message) {
                        Messages.REQUEST_SENT -> {
                            Toast.makeText(context,"Request Sent", Toast.LENGTH_LONG).show()
                        }
                        else -> {}
                    }
                }
                else -> {}
            }
        })
        return binding.root
    }


}