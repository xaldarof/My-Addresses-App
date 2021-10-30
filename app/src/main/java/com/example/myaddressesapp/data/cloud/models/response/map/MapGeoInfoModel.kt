package com.example.myaddressesapp.data.cloud.models.response.map

data class MapGeoInfoModel(
    val plus_code: PlusCode,
    val results: List<Any>,
    val status: String
)