package com.example.myaddressesapp.vm

import androidx.lifecycle.ViewModel
import com.example.myaddressesapp.data.cache.AddressCacheRepository
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import com.example.myaddressesapp.data.cache.models.UserLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel

@Inject constructor (private val cacheRepository: AddressCacheRepository): ViewModel(){

    fun fetchAddresses() = cacheRepository.fetchAddressesAsFlow().map { it.map { it.mapToUiModel() } }

    fun deleteAddress(name: String) = cacheRepository.deleteAddress(name)

    fun addAddress(addressModelDb: AddressModelDb) = cacheRepository.addAddress(addressModelDb)

    fun clearCache() = cacheRepository.clearCache()


    fun saveUserLastLocation(userLocation: UserLocation) {
        cacheRepository.saveUserLastLocation(userLocation)
    }

    suspend fun fetchUserLastLocation(): UserLocation {
        return cacheRepository.fetchUserLastLocation()
    }

}