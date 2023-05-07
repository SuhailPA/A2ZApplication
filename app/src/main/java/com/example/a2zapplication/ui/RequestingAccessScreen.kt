package com.example.a2zapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a2zapplication.R
import com.example.a2zapplication.databinding.FragmentRequestingAccessScreenBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RequestingAccessScreen : Fragment() {

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

        binding.signOut.setOnClickListener {
            Firebase.auth.signOut()
        }
        return binding.root

    }


}