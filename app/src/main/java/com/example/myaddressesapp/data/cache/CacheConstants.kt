package com.example.myaddressesapp.data.cache


object CacheConstants {

    const val DATABASE_NAME = "DB"

    const val LAST_LATITUDE =  "USER_LAST_LATITUDE"
    const val LAST_LONGITUDE =  "USER_LAST_LONGITUDE"
    const val PREFERENCES_NAME = "USER_LAST_LOCATION"

    const val SELECTED_MAP_STYLE = "user_selected_map_style"

}

enum class MapStyle(value:String){
    STANDARD("standart"),
    SPUTNIK("satellite"),
    HYBRID("terrain"),
}