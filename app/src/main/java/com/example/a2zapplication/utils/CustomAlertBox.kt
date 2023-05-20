package com.example.a2zapplication.utils

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.a2zapplication.R
import com.example.a2zapplication.databinding.CustomAlertBoxBinding

class CustomAlertBox(private val context: Context) {
    private lateinit var dialog: AlertDialog
    private lateinit var dialogView: View
    private lateinit var binding: CustomAlertBoxBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = CustomAlertBoxBinding.inflate(inflater)
        dialogView = binding.root
        dialog = AlertDialog.Builder(context).setView(dialogView).create()

    }

    fun setTitle(title: String): CustomAlertBox {
        binding.mainAlertTitle.text = title
        return this
    }

    fun setMessage(message: String): CustomAlertBox {
        binding.mainAlertMessage.text = message
        return this
    }

    fun setPositiveBtn(visibility : Boolean, listener : View.OnClickListener) : CustomAlertBox {
        binding.alertPstiveBtn.isVisible = visibility
        binding.alertPstiveBtn.setOnClickListener {
            listener.onClick(it)
            dialog.dismiss()
        }
        return  this
    }

    fun setAlertBoxType(type : Utils.AlertBoxTypes) : CustomAlertBox {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        when (type) {
            Utils.AlertBoxTypes.NORMAL -> {
                binding.clAlertBox.setBackgroundColor(context.getColor(R.color.customNormalAlertBoxBg))
                binding.mainAlertBox.strokeColor = context.getColor(R.color.customNormalAlertBoxColor)
                binding.alertNgtiveBtn.setColorFilter(context.getColor(R.color.customNormalAlertBoxColor), PorterDuff.Mode.SRC_IN)
                binding.alertPstiveBtn.setColorFilter(context.getColor(R.color.customNormalAlertBoxColor), PorterDuff.Mode.SRC_IN)
                binding.mainAlertTitle.setTextColor(context.getColor(R.color.customNormalAlertBoxColor))
                binding.mainAlertMessage.setTextColor(context.getColor(R.color.customNormalAlertBoxColor))
            }
            Utils.AlertBoxTypes.ERROR -> {
                binding.clAlertBox.setBackgroundColor(context.getColor(R.color.customErrorAlertBoxBg))
                binding.mainAlertBox.strokeColor = context.getColor(R.color.customErrorAlertBoxColor)
                binding.alertNgtiveBtn.setColorFilter(context.getColor(R.color.customErrorAlertBoxColor), PorterDuff.Mode.SRC_IN)
                binding.alertPstiveBtn.setColorFilter(context.getColor(R.color.customErrorAlertBoxColor), PorterDuff.Mode.SRC_IN)
                binding.mainAlertTitle.setTextColor(context.getColor(R.color.customErrorAlertBoxColor))
                binding.mainAlertMessage.setTextColor(context.getColor(R.color.customErrorAlertBoxColor))
            }

            else -> {
                binding.clAlertBox.setBackgroundColor(context.getColor(R.color.customWarningAlertBoxBg))
                binding.mainAlertBox.strokeColor = context.getColor(R.color.customWarningAlertBoxColor)
                binding.alertNgtiveBtn.setColorFilter(context.getColor(R.color.customWarningAlertBoxColor), PorterDuff.Mode.SRC_IN)
                binding.alertPstiveBtn.setColorFilter(context.getColor(R.color.customWarningAlertBoxColor), PorterDuff.Mode.SRC_IN)
                binding.mainAlertTitle.setTextColor(context.getColor(R.color.customWarningAlertBoxColor))
                binding.mainAlertMessage.setTextColor(context.getColor(R.color.customWarningAlertBoxColor))
            }
        }
        return this
    }

    fun setNegativeBtn(visibility: Boolean, listener : View.OnClickListener) : CustomAlertBox {
        binding.alertNgtiveBtn.isVisible = visibility
        binding.alertNgtiveBtn.setOnClickListener {
            listener.onClick(it)
            dialog.dismiss()
        }
        return this
    }

    fun setCancelable(boolean: Boolean) : CustomAlertBox {
        dialog.setCancelable(boolean)
        return this
    }

    fun show(){
        dialog.show()
    }


}