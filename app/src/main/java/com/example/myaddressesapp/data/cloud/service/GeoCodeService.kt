package com.example.myaddressesapp.data.cloud.service

import com.example.myaddressesapp.data.cloud.CloudConstants
import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeService {

    @GET("reverse")
    suspend fun fetchAddressInfo(
        @Query("access_key") access_key:String = CloudConstants.GEO_CODER_KEY,
        @Query("query") query:String
    ): GeoCoderResponseModel

    @GET("reverse")
    suspend fun fetchSingleAddressInfo(
        @Query("access_key") access_key:String = CloudConstants.GEO_CODER_KEY,
        @Query("query") query:String,
        @Query("limit") limit:Int = 1
    ): GeoCoderResponseModel

}