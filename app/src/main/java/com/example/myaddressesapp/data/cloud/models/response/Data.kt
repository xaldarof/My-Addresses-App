package com.example.myaddressesapp.data.cloud.models.response

import com.example.myaddressesapp.data.cloud.models.request.Address
import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("is_deleted")
    val is_deleted: Boolean,
    @SerializedName("completely_deleted")
    val completely_deleted: Boolean,
    @SerializedName("_id")
    val _id:String,
    @SerializedName("name")
    val name:String,

    @SerializedName("address")
    val address:Address,
    @SerializedName("type")
    val type:String,
    @SerializedName("instructions")
    val instructions:String,
    @SerializedName("user_id")
    val user_id:String,
    @SerializedName("created_at")
    val created_at:String,
    @SerializedName("updated_at")
    val updated_at:String,

)