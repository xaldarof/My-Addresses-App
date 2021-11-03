package com.example.myaddressesapp.data.cloud

import com.example.myaddressesapp.data.cache.dao.AddressDao
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.AddressResponseBody
import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseBody
import com.example.myaddressesapp.data.cloud.service.AddressService
import com.example.myaddressesapp.data.cloud.service.GeoCodeService
import javax.inject.Inject

interface GeoCoderRepository {

    suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody

    suspend fun fetchAddressInfo(lat:Double,lon:Double):  GeoCoderResponseBody
    suspend fun addAddress(addressModelDb: AddressModelDb)

    class Base @Inject constructor(
        private val service: AddressService,
        private val geoCodeService: GeoCodeService,
        private val dao: AddressDao
    ) : GeoCoderRepository {

        override suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody {
            return service.createAddress(addressRequestBody)
        }

        override suspend fun fetchAddressInfo(lat: Double,lon: Double): GeoCoderResponseBody {
            return geoCodeService.fetchAddressInfo(lat,lon)
        }

        override suspend fun addAddress(addressModelDb: AddressModelDb) {
            dao.addAddress(addressModelDb)
        }
    }
}