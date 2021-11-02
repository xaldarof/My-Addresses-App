package com.example.myaddressesapp.data.cache

import android.content.SharedPreferences
import com.example.myaddressesapp.data.cache.CacheConstants.SELECTED_MAP_STYLE
import com.example.myaddressesapp.ui.UiConstants
import javax.inject.Inject

interface UserMapStyleDataSource {

    fun saveUserMapStyle(name:String)

    fun fetchUserMapStyle():String?


    class Base @Inject constructor(private val sharedPreferences: SharedPreferences): UserMapStyleDataSource {

        override fun saveUserMapStyle(name: String) {
            sharedPreferences.edit().putString(SELECTED_MAP_STYLE,name).apply()
        }

        override fun fetchUserMapStyle():String? = sharedPreferences.getString(SELECTED_MAP_STYLE, UiConstants.NORMAL_MAP)

    }
}