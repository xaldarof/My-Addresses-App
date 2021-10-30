package com.example.myaddressesapp.data.cloud.models.response

import com.google.gson.annotations.SerializedName

data class AddressResponseBody(
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data,
    @SerializedName("time")
    val time: String
)