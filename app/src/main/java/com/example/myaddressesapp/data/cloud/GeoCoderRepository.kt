package com.example.myaddressesapp.data.cloud

import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.AddressResponseBody
import com.example.myaddressesapp.data.cloud.models.response.map.Data
import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseModel
import javax.inject.Inject

interface GeoCoderRepository {

    suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody

    suspend fun fetchGeoCodeInfo(query:String):GeoCoderResponseModel
    suspend fun fetchSingleCodeInfo(query: String):GeoCoderResponseModel

    class Base @Inject constructor(private val service: AddressService,
                                   private val geoCodeService: GeoCodeService): GeoCoderRepository {

        override suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody {
            return service.createAddress(addressRequestBody)
        }

        override suspend fun fetchGeoCodeInfo(query: String): GeoCoderResponseModel {
            return geoCodeService.fetchGeoCodeInfo(query = query)
        }

        override suspend fun fetchSingleCodeInfo(query: String): GeoCoderResponseModel {
            return geoCodeService.fetchSingleCodeInfo(query=query)
        }
    }
}