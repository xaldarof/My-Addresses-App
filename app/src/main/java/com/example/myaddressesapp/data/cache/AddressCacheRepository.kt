package com.example.myaddressesapp.data.cache

import com.example.myaddressesapp.data.cache.dao.AddressDao
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import com.example.myaddressesapp.data.cache.models.UserLocation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface AddressCacheRepository {

    fun fetchAddressesAsFlow(): Flow<List<AddressModelDb>>
    fun fetchAddresses(): List<AddressModelDb>

    fun deleteAddress(name:String)
    fun addAddress(addressModelDb: AddressModelDb)
    fun clearCache()

    fun saveUserLastLocation(userLocation: UserLocation)
    suspend fun fetchUserLastLocation(): UserLocation

    class Base @Inject constructor(private val dao: AddressDao,
                                   private val userLastLocationDataSource: UserLastLocationDataSource):
        AddressCacheRepository {

        override fun fetchAddressesAsFlow(): Flow<List<AddressModelDb>> = dao.fetchAddressesAsFlow()

        override fun fetchAddresses(): List<AddressModelDb> = dao.fetchAddresses()

        override fun deleteAddress(name: String) = dao.deleteAddress(name)

        override fun addAddress(addressModelDb: AddressModelDb) = dao.addAddress(addressModelDb)

        override fun clearCache() = dao.clearCache()


        override fun saveUserLastLocation(userLocation: UserLocation) {
            userLastLocationDataSource.saveUserLastLocation(userLocation)
        }

        override suspend fun fetchUserLastLocation(): UserLocation {
            return userLastLocationDataSource.fetchUserLastLocation()
        }
    }
}