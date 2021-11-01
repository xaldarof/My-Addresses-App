package com.example.myaddressesapp.data.cloud

import com.example.myaddressesapp.data.cache.dao.AddressDao
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.AddressResponseBody
import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseModel
import com.example.myaddressesapp.data.cloud.service.AddressService
import com.example.myaddressesapp.data.cloud.service.GeoCodeService
import javax.inject.Inject

interface GeoCoderRepository {

    suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody

    suspend fun fetchAddressInfo(query: String): GeoCoderResponseModel
    suspend fun fetchSingleAddressInfo(query: String): GeoCoderResponseModel
    suspend fun addAddress(addressModelDb: AddressModelDb)

    class Base @Inject constructor(
        private val service: AddressService,
        private val geoCodeService: GeoCodeService,
        private val dao: AddressDao
    ) : GeoCoderRepository {

        override suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody {
            return service.createAddress(addressRequestBody)
        }

        override suspend fun fetchAddressInfo(query: String): GeoCoderResponseModel {
            return geoCodeService.fetchAddressInfo(query = query)
        }

        override suspend fun fetchSingleAddressInfo(query: String): GeoCoderResponseModel {
            return geoCodeService.fetchSingleAddressInfo(query = query)
        }

        override suspend fun addAddress(addressModelDb: AddressModelDb) {
            dao.addAddress(addressModelDb)
        }
    }
}