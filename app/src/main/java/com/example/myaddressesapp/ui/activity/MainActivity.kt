package com.example.myaddressesapp.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.example.myaddressesapp.R
import com.example.myaddressesapp.data.cloud.AddressService
import com.example.myaddressesapp.data.cloud.models.request.Address
import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.utils.setTransparentStatusBar
import com.example.myaddressesapp.databinding.ActivityMainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

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
            arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
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