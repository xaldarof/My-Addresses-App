package com.example.myaddressesapp.data.cloud

import com.example.myaddressesapp.data.cloud.models.response.map.MapGeoInfoModel
import retrofit2.http.POST
import retrofit2.http.Query

interface GeoCodeService {

    @POST("geocode/json")
    suspend fun fetchGeoCodeInfo(
        @Query("latlng") latlng:String,
        @Query("key") key:String
    ): MapGeoInfoModel

}