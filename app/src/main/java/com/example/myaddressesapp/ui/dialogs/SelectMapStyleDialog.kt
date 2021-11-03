package com.example.myaddressesapp.ui.dialogs

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.example.myaddressesapp.R
import com.example.myaddressesapp.databinding.MapItemLayoutBinding
import com.example.myaddressesapp.databinding.SelectMapStyleLayoutBinding
import com.example.myaddressesapp.ui.adapter.SelectMapStyleAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.lang.NullPointerException

interface SelectMapStyleDialog {

    fun show()

    class Base(private val context: Context,private val callBack: CallBack,
               private val selectedMap:String): SelectMapStyleDialog ,SelectMapStyleAdapter.CallBack {

        private lateinit var bottomSheetDialog: BottomSheetDialog

        override fun show() {
            bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
            val binding = SelectMapStyleLayoutBinding.inflate(bottomSheetDialog.layoutInflater)
            bottomSheetDialog.setContentView(binding.root)
            bottomSheetDialog.dismissWithAnimation = true

            val adapter = SelectMapStyleAdapter(this,selectedMap)
            binding.rv.adapter = adapter

            binding.cancelBtn.setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show()
        }

        override fun onSelectLocation(name: String) {
            callBack.onSelectMapStyle(name)
            bottomSheetDialog.dismiss()
        }
    }

    interface CallBack {
        fun onSelectMapStyle(name:String)
    }
}