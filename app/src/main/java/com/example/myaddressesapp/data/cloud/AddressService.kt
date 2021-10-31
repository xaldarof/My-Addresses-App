package com.example.myaddressesapp.data.cloud

import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.AddressResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface AddressService {

    @POST("address")
    suspend fun createAddress(@Body addressRequestBody: AddressRequestBody):AddressResponseBody

}