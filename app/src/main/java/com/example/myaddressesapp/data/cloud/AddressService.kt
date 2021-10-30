package com.example.myaddressesapp.data.cloud

import com.example.myaddressesapp.data.cloud.models.PostModel
import retrofit2.http.GET


interface AddressService {

    @GET("posts")
    fun fetchAddress():PostModel
}