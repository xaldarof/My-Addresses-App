package com.example.myaddressesapp.data.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myaddressesapp.ui.models.AddressUiModel
import java.io.Serializable

@Entity(tableName = "addresses")
data class AddressModelDb(

    @PrimaryKey
    val name:String,

    val latitude:Double,
    val longitude:Double
):Serializable {

    fun mapToUiModel():AddressUiModel {
        return AddressUiModel(name, latitude, longitude)
    }
}