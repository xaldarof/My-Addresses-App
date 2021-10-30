package com.example.myaddressesapp.vm

import androidx.lifecycle.ViewModel
import com.example.myaddressesapp.data.cloud.MapRepository
import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.AddressResponseBody
import com.example.myaddressesapp.data.cloud.models.response.map.MapGeoInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommonViewModel

@Inject constructor(private val repository: MapRepository.Base): ViewModel() {


    suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody {
        return repository.createAddress(addressRequestBody)
    }

    suspend fun fetchGeoCodeInfo(latlng: String, key: String): MapGeoInfoModel {
        return repository.fetchGeoCodeInfo(latlng,key)
    }
}