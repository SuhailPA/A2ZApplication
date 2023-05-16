package com.example.a2zapplication.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.a2zapplication.R
import com.example.a2zapplication.databinding.DialogCustomProgressBarBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.dialog.MaterialDialogs
import com.google.android.material.textview.MaterialTextView

class CustomProgressDialog (context : Context) : AlertDialog.Builder(context) {
    private val binding : DialogCustomProgressBarBinding = DialogCustomProgressBarBinding.inflate(
        LayoutInflater.from(context))


    private var dialog: AlertDialog? = null

    init {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setView(binding.root)
        dialog = alertDialogBuilder.create()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
    }

    fun setText (message : String){
        binding.progressText.text = message
    }


    fun showDialog(){
        dialog?.show()
    }
    fun dismiss() {
        dialog?.dismiss()
    }

}