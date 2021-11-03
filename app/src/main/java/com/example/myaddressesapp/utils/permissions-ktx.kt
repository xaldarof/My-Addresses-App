package com.example.myaddressesapp.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

fun Activity.isLocationPermissionGranted(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this, android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestLocationPermission(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (isLocationPermissionGranted()) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
    }
}