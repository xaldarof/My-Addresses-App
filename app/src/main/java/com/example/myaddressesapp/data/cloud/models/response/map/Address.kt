package com.example.myaddressesapp.data.cloud.models.response.map

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("country_code")
    val country_code: String,
    @SerializedName("house_number")
    val house_number: String,
    @SerializedName("neighbourhood")
    val neighbourhood: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("road")
    val road: String
)