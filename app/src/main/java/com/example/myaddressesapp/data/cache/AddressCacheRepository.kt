package com.example.myaddressesapp.data.cache

import com.example.myaddressesapp.data.cache.dao.AddressDao
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface AddressCacheRepository {

    fun fetchAddressesAsFlow(): Flow<List<AddressModelDb>>
    fun fetchAddresses(): List<AddressModelDb>

    fun deleteAddress(name:String)
    fun addAddress(addressModelDb: AddressModelDb)
    fun clearCache()

    class Base @Inject constructor(private val dao: AddressDao): AddressCacheRepository {

        override fun fetchAddressesAsFlow(): Flow<List<AddressModelDb>> = dao.fetchAddressesAsFlow()

        override fun fetchAddresses(): List<AddressModelDb> = dao.fetchAddresses()

        override fun deleteAddress(name: String) = dao.deleteAddress(name)

        override fun addAddress(addressModelDb: AddressModelDb) = dao.addAddress(addressModelDb)

        override fun clearCache() = dao.clearCache()
    }
}