package com.example.myaddressesapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myaddressesapp.data.cloud.AddressService

import com.example.myaddressesapp.data.utils.isLocationPermissionGranted
import com.example.myaddressesapp.databinding.FragmentMapBinding
import com.example.myaddressesapp.vm.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment() , OnMapReadyCallback{

    private lateinit var binding:FragmentMapBinding
    private val viewModel:MainViewModel by viewModels()

    @Inject
    lateinit var service: AddressService

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        binding.historyBtn.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToHistoryFragment())
        }
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