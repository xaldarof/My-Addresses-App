package com.example.myaddressesapp.data.cloud.models.response.map

import com.example.myaddressesapp.data.cache.models.AddressModelDb
import com.example.myaddressesapp.ui.models.AddressUiModel

data class Data(
    val administrative_area: Any,
    val confidence: Double,
    val continent: String,
    val country: String,
    val country_code: String,
    val county: String,
    val distance: Double,
    val label: String,
    val latitude: Double,
    val locality: String,
    val longitude: Double,
    val name: String,
    val neighbourhood: String,
    val number: String,
    val postal_code: String,
    val region: String,
    val region_code: String,
    val street: String,
    val type: String
){
    fun mapToDbModel():AddressModelDb {
        return AddressModelDb(label,latitude,longitude)
    }

    fun mapToUiModel():AddressUiModel {
        return AddressUiModel(label,latitude,longitude)
    }
}