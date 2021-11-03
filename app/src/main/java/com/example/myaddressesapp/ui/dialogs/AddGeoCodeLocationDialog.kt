package com.example.myaddressesapp.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseBody
import com.example.myaddressesapp.databinding.SaveBottomSheetLayoutBinding
import com.example.myaddressesapp.ui.models.AddressUiModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.coordinatorlayout.widget.CoordinatorLayout
import android.widget.FrameLayout

class AddGeoCodeLocationDialog(private val callBack: CallBack,
           private val geoCoderResponseBody: GeoCoderResponseBody): BottomSheetDialogFragment(){

    private lateinit var binding:SaveBottomSheetLayoutBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.example.myaddressesapp.R.id.design_bottom_sheet) as FrameLayout?
            val coordinatorLayout = bottomSheet!!.parent as CoordinatorLayout
            val bottomSheetBehavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.peekHeight = bottomSheet.height
            coordinatorLayout.parent.requestLayout()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), com.example.myaddressesapp.R.style.BottomSheetDialogTheme)
        binding = SaveBottomSheetLayoutBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)

        binding.latitudeEdiText.setText(geoCoderResponseBody.lat)
        binding.longitudeEdiText.setText(geoCoderResponseBody.lon)
        binding.newNameEdiText.setText(geoCoderResponseBody.display_name)

        geoCoderResponseBody.boundingbox.forEach {
            val chip = Chip(context)
            chip.text = it
            binding.chipGroup.addView(chip)
        }

        binding.saveBtn.setOnClickListener {

            val name = binding.newNameEdiText.text.toString()
            val latitude = binding.latitudeEdiText.text.toString()
            val longitude = binding.longitudeEdiText.text.toString()
            val formattedUiModel = AddressUiModel(name,latitude.toDouble(),longitude.toDouble())

            saveUserLocation(formattedUiModel)
        }

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        return dialog
    }

    private fun saveUserLocation(formattedUiModel: AddressUiModel) {
        callBack.onClickSaveGeoCodeLocation(formattedUiModel)
        Toast.makeText(binding.root.context, com.example.myaddressesapp.R.string.success_save, Toast.LENGTH_SHORT).show()
        dismiss()
    }

    interface CallBack {
        fun onClickSaveGeoCodeLocation(uiModel: AddressUiModel)
    }
}
