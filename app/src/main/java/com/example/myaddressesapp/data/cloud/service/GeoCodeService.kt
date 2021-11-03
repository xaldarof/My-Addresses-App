package com.example.myaddressesapp.data.cloud.service

import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeService {

    @GET("reverse?format=json")
    suspend fun fetchAddressInfo(
            @Query("lat") access_key:Double,
        @Query("lon") query:Double
    ): GeoCoderResponseBody

}