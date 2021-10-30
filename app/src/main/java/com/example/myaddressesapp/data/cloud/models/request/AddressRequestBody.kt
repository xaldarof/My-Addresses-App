package com.example.myaddressesapp.data.cloud.models.request

import com.google.gson.annotations.SerializedName

data class AddressRequestBody(

    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: Address,
    @SerializedName("type")
    val type: String,
    @SerializedName("instructions")
    val instructions:String

    )