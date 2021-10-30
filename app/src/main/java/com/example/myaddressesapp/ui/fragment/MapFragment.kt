package com.example.myaddressesapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myaddressesapp.data.cloud.AddressService
import com.example.myaddressesapp.data.cloud.CloudConstants

import com.example.myaddressesapp.data.utils.isLocationPermissionGranted
import com.example.myaddressesapp.databinding.FragmentMapBinding
import com.example.myaddressesapp.vm.CommonViewModel
import com.google.android.gms.dynamic.ObjectWrapper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment() , OnMapReadyCallback{

    private lateinit var binding:FragmentMapBinding
    private val viewModel:CommonViewModel by viewModels()

    @Inject
    lateinit var service: AddressService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        val loc = LatLng(37.422160,-122.084270)
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,15f))
        binding.mapView.onResume()
        p0.isMyLocationEnabled = requireActivity().isLocationPermissionGranted()

        setOnMapClick(p0)

    }

    private fun setOnMapClick(googleMap: GoogleMap){
        googleMap.setOnMapClickListener {
            createMarkerAt(googleMap,LatLng(it.latitude,it.longitude))
        }
    }

    private fun createMarkerAt(googleMap: GoogleMap,latLng: LatLng){
        googleMap.addMarker(MarkerOptions().position(latLng))
    }
}