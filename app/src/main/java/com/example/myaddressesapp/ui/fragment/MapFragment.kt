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
import androidx.navigation.fragment.navArgs
import com.example.myaddressesapp.data.cloud.models.request.Address
import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.map.Data
import com.example.myaddressesapp.data.utils.isLocationPermissionGranted
import com.example.myaddressesapp.databinding.FragmentMapBinding
import com.example.myaddressesapp.ui.adapter.BottomSheetRecyclerAdapter
import com.example.myaddressesapp.vm.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, BottomSheetRecyclerAdapter.CallBack,GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MainViewModel by viewModels()
    private val args by navArgs<MapFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
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
        setOnMapClick(p0)
        p0.isMyLocationEnabled = requireActivity().isLocationPermissionGranted()
        binding.mapView.onResume()

        val loc = LatLng(37.422160, -122.084270)
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 15f))

    }

    private suspend fun setBottomSheetData(query: String) {
        val adapter = BottomSheetRecyclerAdapter(this)
        binding.bottom.rv.adapter = adapter
        val geoData = viewModel.fetchGeoCodeInfo(query)
        adapter.update(geoData.data)

        BottomSheetBehavior.from(binding.bottom.bottomContainer).apply {
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setOnMapClick(googleMap: GoogleMap) {
        googleMap.setOnMapClickListener {
            val clickedLocation = LatLng(it.latitude, it.longitude)
            createMarkerAt(googleMap, clickedLocation)

            lifecycleScope.launch {
                val currentLocation = "${it.latitude},${it.longitude}"
                setBottomSheetData(currentLocation)
            }
        }
    }

    private  fun createMarkerAt(googleMap: GoogleMap, latLng: LatLng) {
        googleMap.addMarker(MarkerOptions().position(latLng))
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onClickAddLocation(data: Data) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
//               val response =
//                   viewModel.createAddress(AddressRequestBody(data.label, Address(data.name, data.latitude,
//                    data.longitude), data.type, ""))
                viewModel.addGeoCode(data.mapToDbModel())
            }
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        lifecycleScope.launch {
            setBottomSheetData("${p0.position.latitude},${p0.position.longitude}")
        }
        return true
    }
}