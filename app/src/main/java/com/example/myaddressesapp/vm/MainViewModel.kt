package com.example.myaddressesapp.vm

import androidx.lifecycle.ViewModel
import com.example.myaddressesapp.data.cloud.GeoCoderRepository
import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.AddressResponseBody
import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseModel
import com.example.myaddressesapp.ui.models.AddressUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel

@Inject constructor(private val repository: GeoCoderRepository.Base): ViewModel() {


    suspend fun createAddress(addressRequestBody: AddressRequestBody): AddressResponseBody {
        return repository.createAddress(addressRequestBody)
    }

    suspend fun fetchGeoCodeInfo(query: String): GeoCoderResponseModel {
        return repository.fetchAddressInfo(query)
    }

    suspend fun fetchSingleCodeInfo(query: String): GeoCoderResponseModel {
        return repository.fetchSingleAddressInfo(query)
    }

    suspend fun addGeoCode(uiModel: AddressUiModel){
        repository.addAddress(uiModel.mapToDbModel())
    }
}