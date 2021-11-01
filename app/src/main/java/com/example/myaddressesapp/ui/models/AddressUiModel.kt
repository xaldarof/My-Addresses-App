package com.example.myaddressesapp.ui.models

import android.os.Parcelable
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressUiModel(
    val name:String,
    val latitude:Double,
    val longitude:Double
):Parcelable {

    fun mapToDbModel():AddressModelDb{
        return AddressModelDb(name, latitude, longitude)
    }
}