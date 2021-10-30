package com.example.myaddressesapp.data.cache.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class AddressModelDb(

    @PrimaryKey
    val name:String,

    val latitude:Double,
    val longitude:Double
)