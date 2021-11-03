package com.example.myaddressesapp.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.myaddressesapp.utils.setTransparentStatusBar
import com.example.myaddressesapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setTransparentStatusBar()
        requestPermission()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)){

            }else {
                requestPermission()
            }
        }
    }
}