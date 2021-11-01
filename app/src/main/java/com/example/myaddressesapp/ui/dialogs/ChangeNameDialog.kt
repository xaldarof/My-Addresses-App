package com.example.myaddressesapp.ui.dialogs

import android.content.Context
import android.widget.Toast
import com.example.myaddressesapp.R
import com.example.myaddressesapp.databinding.SaveBottomSheetLayoutBinding
import com.example.myaddressesapp.ui.models.AddressUiModel
import com.google.android.material.bottomsheet.BottomSheetDialog


interface ChangeNameDialog {

    fun show(uiModel: AddressUiModel,callBack: CallBack)

    class Base(private val context: Context): ChangeNameDialog {

        override fun show(uiModel: AddressUiModel,callBack: CallBack) {
            val dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
            val binding = SaveBottomSheetLayoutBinding.inflate(dialog.layoutInflater)
            dialog.setContentView(binding.root)

            binding.newNameEdiText.setText(uiModel.name)
            binding.latitudeEdiText.setText(uiModel.latitude.toString())
            binding.longitudeEdiText.setText(uiModel.longitude.toString())

            binding.saveBtn.setOnClickListener {
                val name = binding.newNameEdiText.text.toString()
                val latitude = binding.latitudeEdiText.text.toString()
                val longitude = binding.longitudeEdiText.text.toString()
                val formattedUiModel = AddressUiModel(name,latitude.toDouble(),longitude.toDouble())

                callBack.onClickSave(formattedUiModel)
                Toast.makeText(binding.root.context, R.string.success_save, Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            binding.cancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    interface CallBack {
        fun onClickSave(uiModel: AddressUiModel)
    }
}