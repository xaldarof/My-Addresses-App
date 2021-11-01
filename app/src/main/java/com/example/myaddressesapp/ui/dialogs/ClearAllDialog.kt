package com.example.myaddressesapp.ui.dialogs

import android.content.Context
import com.example.myaddressesapp.R
import com.example.myaddressesapp.databinding.DeleteLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

interface ClearAllDialog {

    fun show(callBack: CallBack)

    class Base(private val context: Context) : ClearAllDialog {

        override fun show(callBack: CallBack) {
            val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
            val binding = DeleteLayoutBinding.inflate(bottomSheetDialog.layoutInflater)
            bottomSheetDialog.setContentView(binding.root)

            binding.deleteBtn.setOnClickListener {
                callBack.onClickClear()
                bottomSheetDialog.dismiss()
            }

            binding.cancelBtn.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }
    }

    interface CallBack {
        fun onClickClear()
    }
}