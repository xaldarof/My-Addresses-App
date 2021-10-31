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
import androidx.navigation.fragment.findNavController
import com.example.myaddressesapp.data.cloud.AddressService
import com.example.myaddressesapp.data.cloud.CloudConstants
import com.example.myaddressesapp.data.cloud.GeoCodeService

import com.example.myaddressesapp.data.utils.isLocationPermissionGranted
import com.example.myaddressesapp.databinding.FragmentMapBinding
import com.example.myaddressesapp.ui.adapter.BottomSheetRecyclerAdapter
import com.example.myaddressesapp.vm.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject


@AndroidEntryPoint
class MapFragment : Fragment() , OnMapReadyCallback{

    private lateinit var binding:FragmentMapBinding
    private val viewModel:MainViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater,container,false)
        BottomSheetBehavior.from(binding.bottom.bottomContainer).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 200
        }
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

    private suspend fun setBottomSheetData(query:String){
        val adapter = BottomSheetRecyclerAdapter()
        binding.bottom.rv.adapter = adapter
        val geoData = viewModel.fetchGeoCodeInfo(query)
        adapter.update(geoData.data)

        BottomSheetBehavior.from(binding.bottom.bottomContainer).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setOnMapClick(googleMap: GoogleMap){
        googleMap.setOnMapClickListener {
            val clickedLocation = LatLng(it.latitude,it.longitude)
            createMarkerAt(googleMap,clickedLocation)

            lifecycleScope.launch {
                val currentLocation = "${it.latitude},${it.longitude}"
                val response = viewModel.fetchSingleCodeInfo(query = currentLocation)
                Log.d("res","LOC = $currentLocation $$response")
                setBottomSheetData(currentLocation)
            }
        }
    }

    private fun createMarkerAt(googleMap: GoogleMap,latLng: LatLng){
        googleMap.addMarker(MarkerOptions().position(latLng))
    }
}