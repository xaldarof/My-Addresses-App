package com.example.myaddressesapp.data.cloud.models.response.map

import com.google.gson.annotations.SerializedName

data class GeoCoderResponseBody(
    @SerializedName("address")
    val address: Address,
    @SerializedName("boundingbox")
    val boundingbox: List<String>,
    @SerializedName("display_name")
    val display_name: String,
    @SerializedName("lat")
    val lat: String,
    @SerializedName("licence")
    val licence: String,
    @SerializedName("lon")
    val lon: String,
    @SerializedName("osm_id")
    val osm_id: Long,
    @SerializedName("osm_type")
    val osm_type: String,
    @SerializedName("place_id")
    val place_id: Long
)