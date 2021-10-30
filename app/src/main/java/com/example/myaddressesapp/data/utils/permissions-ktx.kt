package com.example.myaddressesapp.data.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

fun Activity.isLocationPermissionGranted(): Boolean {
    return ActivityCompat.checkSelfPermission(
        this, android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}