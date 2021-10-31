package com.example.myaddressesapp.data.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myaddressesapp.ui.models.AddressUiModel

@Entity(tableName = "addresses")
data class AddressModelDb(

    @PrimaryKey
    val name:String,

    val latitude:Double,
    val longitude:Double
){
    fun mapToUiModel():AddressUiModel {
        return AddressUiModel(name, latitude, longitude)
    }
}