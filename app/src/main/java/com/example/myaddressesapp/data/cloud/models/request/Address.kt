package com.example.myaddressesapp.data.cloud.models.request

import com.google.gson.annotations.SerializedName

data class Address(

    @SerializedName("location_name")
    val locationName: String,

    @SerializedName("latitude")
    val latitude:Double,
    @SerializedName("longitude")
    val longitude:Double

)