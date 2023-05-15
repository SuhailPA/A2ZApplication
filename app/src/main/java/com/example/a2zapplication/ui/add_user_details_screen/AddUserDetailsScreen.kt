package com.example.a2zapplication.ui.add_user_details_screen

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.DrawableContainer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.a2zapplication.R
import com.example.a2zapplication.data.model.firebase.User
import com.example.a2zapplication.databinding.FragmentAddUserDetailsScreenBinding
import com.example.a2zapplication.utils.AllEvents
import com.example.a2zapplication.utils.Messages
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddUserDetailsScreen : Fragment() {
    private val viewModel: AddUserDetailsViewModel by viewModels()
    lateinit var binding: FragmentAddUserDetailsScreenBinding

    private var items = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private val args: AddUserDetailsScreenArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddUserDetailsScreenBinding.inflate(inflater, container, false)
        initUI()
        return binding.root
    }

    private fun initUI() {
        initDropDownUI()
        observeTheResponce()
        binding.apply {
            /**
             * Getting the dropdown list items from firebase
             */
            lifecycleScope.launch {
                viewModel.fetchClassDetails().collect {
                    items.clear()
                    items.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }

            /**
             * If we already got those details from login will set here initially
             */
            userName.editText?.setText(args.UserDetails.name.orEmpty())
            email.editText?.setText(args.UserDetails.emailId.orEmpty())
            number.editText?.setText(args.UserDetails.number.orEmpty())

            classTextView.setOnItemClickListener { _, _, _, _ -> }

            /**
             * While clicking on the store Button will store these details in DB
             */
            storeDetailsInDB.setOnClickListener {
                if (userName.isValidated(FieldType.NAME) && email.isValidated(FieldType.EMAIL) && number.isValidated(
                        FieldType.NUMBER)
                ) {
                    if (classTextView.text.toString().isNotEmpty()){
                        val user = User(
                           uid= args.UserDetails.uid,
                           name = userName.editText?.text.toString(),
                            emailId = email.editText?.text?.toString(),
                           standard =  classTextView.text.toString(),
                            number = number.editText?.text.toString(),
                            photoUrl = args.UserDetails.photoUrl.orEmpty(),
                            accessApproved = false
                        )
                        viewModel.sendRequestForAccess(user)
                    }else {
                        Toast.makeText(context, "Select the class", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun observeTheResponce() {
        viewModel.allEvents.observe(viewLifecycleOwner, Observer {
            when (it) {
                is AllEvents.Message -> {
                    when(it.message) {
                        Messages.REQUEST_SENT -> Toast.makeText(context,"Successfully sented the request",Toast.LENGTH_SHORT).show()
                        else -> {}
                    }
                }
                else ->{}
            }
        })
    }

    private fun initDropDownUI() {
        binding.classTextView.setDropDownBackgroundResource(R.drawable.custom_dropdown_menu)
        adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }


    private fun TextInputLayout.isValidated(type: FieldType): Boolean {
        this.editText?.let {
            if (it.text.toString().isEmpty()) {
                it.error = "Field is mandatory"
                it.isFocusable = true
                return false
            }
            when (type) {
                FieldType.NAME -> {
                    if (it.text.length < 3) {
                        it.error = "Name should be at least 4 characters"
                        return false
                    }
                }

                FieldType.NUMBER -> {
                    if (it.text.length != 10) {
                        it.error = "Kindly enter valid mobile number"
                        return false
                    }

                }

                FieldType.EMAIL -> {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it.text.toString())
                            .matches()
                    ) {
                        it.error = "Kindly enter the valid emailID"
                        return false
                    }
                }
            }
        }
        return true
    }

    enum class FieldType {
        NAME,
        EMAIL,
        NUMBER
    }
}