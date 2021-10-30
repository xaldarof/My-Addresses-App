package com.example.myaddressesapp.vm

import androidx.lifecycle.ViewModel
import com.example.myaddressesapp.data.cache.AddressCacheRepository
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel

@Inject constructor (private val cacheRepository: AddressCacheRepository): ViewModel(){

    fun fetchAddresses() = cacheRepository.fetchAddresses()

    fun deleteAddress(name: String) = cacheRepository.deleteAddress(name)

    fun addAddress(addressModelDb: AddressModelDb) = cacheRepository.addAddress(addressModelDb)

}