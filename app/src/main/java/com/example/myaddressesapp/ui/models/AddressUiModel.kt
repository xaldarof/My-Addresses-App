package com.example.myaddressesapp.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressUiModel(
    val name:String,
    val latitude:Double,
    val longitude:Double
):Parcelable