package com.example.myaddressesapp.vm

import androidx.lifecycle.ViewModel
import com.example.myaddressesapp.data.cache.AddressCacheRepository
import com.example.myaddressesapp.data.cache.models.UserLocation
import com.example.myaddressesapp.data.cloud.GeoCoderRepository
import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.ui.models.AddressUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel

@Inject constructor(private val repository: GeoCoderRepository.Base,
                    private val cacheRepository: AddressCacheRepository): ViewModel() {

    fun saveUserLastLocation(userLocation: UserLocation) = cacheRepository.saveUserLastLocation(userLocation)

    fun saveUserMapStyle(name:String) = cacheRepository.saveUserMapStyle(name)


    fun fetchUserMapStyle():String? = cacheRepository.fetchUserMapStyle()


    suspend fun fetchUserLastLocation() = cacheRepository.fetchUserLastLocation()


    suspend fun createAddress(addressRequestBody: AddressRequestBody) = repository.createAddress(addressRequestBody)


    suspend fun fetchGeoCodeInfo(lat:Double,lon:Double) = repository.fetchAddressInfo(lat,lon)

    suspend fun addGeoCode(uiModel: AddressUiModel) = repository.addAddress(uiModel.mapToDbModel())



    fun fetchUserLastZoom():Float = cacheRepository.fetchUserLastZoom()
    fun saveUserLastZoom(zoom:Float) = cacheRepository.saveUserLastZoom(zoom)
}