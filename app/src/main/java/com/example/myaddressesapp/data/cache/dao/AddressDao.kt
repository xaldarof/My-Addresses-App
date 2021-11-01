package com.example.myaddressesapp.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import kotlinx.coroutines.flow.Flow


@Dao
interface AddressDao  {

    @Query("SELECT * FROM addresses")
    fun fetchAddressesAsFlow():Flow<List<AddressModelDb>>

    @Query("SELECT * FROM addresses")
    fun fetchAddresses():List<AddressModelDb>


    @Query("DELETE FROM addresses WHERE name=:name")
    fun deleteAddress(name:String)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAddress(addressModelDb: AddressModelDb)


}