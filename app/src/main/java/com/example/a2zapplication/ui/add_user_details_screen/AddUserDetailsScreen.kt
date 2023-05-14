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
import androidx.annotation.DrawableRes
import com.example.a2zapplication.R
import com.example.a2zapplication.databinding.FragmentAddUserDetailsScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserDetailsScreen : Fragment() {
    lateinit var binding : FragmentAddUserDetailsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddUserDetailsScreenBinding.inflate(inflater,container,false)
        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.classTextView.setDropDownBackgroundResource(R.drawable.custom_dropdown_menu)
        return binding.root
    }


}