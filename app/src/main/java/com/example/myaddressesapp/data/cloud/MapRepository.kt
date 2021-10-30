package com.example.myaddressesapp.data.cloud

import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.AddressResponseBody
import com.example.myaddressesapp.data.cloud.models.response.map.MapGeoInfoModel
import javax.inject.Inject

interface MapRepository {

    suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody

    suspend fun fetchGeoCodeInfo(latlng:String,key:String):MapGeoInfoModel

    class Base @Inject constructor(private val service: AddressService,
                                   private val geoCodeService: GeoCodeService): MapRepository {

        override suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody {
            return service.createAddress(addressRequestBody)
        }

        override suspend fun fetchGeoCodeInfo(latlng: String, key: String): MapGeoInfoModel {
            return geoCodeService.fetchGeoCodeInfo(latlng,key)
        }
    }
}