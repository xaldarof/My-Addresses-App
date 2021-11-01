package com.example.myaddressesapp.vm

import androidx.lifecycle.ViewModel
import com.example.myaddressesapp.data.cache.AddressCacheRepository
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel

@Inject constructor (private val cacheRepository: AddressCacheRepository): ViewModel(){

    fun fetchAddresses() = cacheRepository.fetchAddressesAsFlow().map { it.map { it.mapToUiModel() } }

    fun deleteAddress(name: String) = cacheRepository.deleteAddress(name)

    fun addAddress(addressModelDb: AddressModelDb) = cacheRepository.addAddress(addressModelDb)

}