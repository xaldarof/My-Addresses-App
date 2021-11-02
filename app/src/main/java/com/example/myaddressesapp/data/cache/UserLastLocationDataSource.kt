package com.example.myaddressesapp.data.cache

import android.content.SharedPreferences
import com.example.myaddressesapp.data.cache.CacheConstants.LAST_LATITUDE
import com.example.myaddressesapp.data.cache.CacheConstants.LAST_LONGITUDE
import com.example.myaddressesapp.data.cache.models.UserLocation
import javax.inject.Inject

interface UserLastLocationDataSource {

    fun saveUserLastLocation(userLocation: UserLocation)
    suspend fun fetchUserLastLocation(): UserLocation

    class Base @Inject constructor(private val sharedPreferences: SharedPreferences): UserLastLocationDataSource {

        override fun saveUserLastLocation(userLocation: UserLocation) {
            sharedPreferences.edit().putFloat(LAST_LATITUDE,userLocation.lat.toFloat()).apply()
            sharedPreferences.edit().putFloat(LAST_LONGITUDE,userLocation.lon.toFloat()).apply()
        }

        override suspend fun fetchUserLastLocation(): UserLocation {
            return UserLocation(sharedPreferences.getFloat(LAST_LATITUDE,0.0f).toDouble(),
                sharedPreferences.getFloat(LAST_LONGITUDE,0.0f).toDouble()
            )
        }
    }
}