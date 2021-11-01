package com.example.myaddressesapp.utils

import com.google.android.gms.maps.GoogleMap


fun GoogleMap.formatToPosition():String {
    return "${this.cameraPosition.target.latitude},${this.cameraPosition.target.longitude}"
}