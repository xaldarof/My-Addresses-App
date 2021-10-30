package com.example.myaddressesapp.data.cloud

import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.AddressResponseBody
import com.example.myaddressesapp.data.cloud.models.response.map.MapGeoInfoModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AddressService {

    @POST("address")
    suspend fun createAddress(@Body addressRequestBody: AddressRequestBody):AddressResponseBody

}