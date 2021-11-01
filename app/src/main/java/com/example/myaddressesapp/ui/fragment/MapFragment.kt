package com.example.myaddressesapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myaddressesapp.R
import com.example.myaddressesapp.data.cloud.models.response.map.Data
import com.example.myaddressesapp.data.utils.*
import com.example.myaddressesapp.databinding.FragmentMapBinding
import com.example.myaddressesapp.ui.UiConstants
import com.example.myaddressesapp.ui.adapter.BottomSheetRecyclerAdapter
import com.example.myaddressesapp.vm.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment: Fragment(), OnMapReadyCallback, BottomSheetRecyclerAdapter.CallBack,
    GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MainViewModel by viewModels()

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
        setOnCameraChangeListener(p0)
        p0.isMyLocationEnabled = requireActivity().isLocationPermissionGranted()
        binding.mapView.onResume()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<LatLng>(UiConstants.ADDRESS_ARG)
            ?.observe(viewLifecycleOwner, {
                navigateCamera(p0, it)
            })

        binding.bottom.addLocationButton.setOnClickListener {
            lifecycleScope.launch {
                val currentLocation = p0.formatToPosition()
                if (currentLocation.isNotEmpty()) {
                    val test = viewModel.fetchSingleCodeInfo(currentLocation).data[0].mapToDbModel()
                    viewModel.addGeoCode(test)
                    Toast.makeText(requireContext(), R.string.success_save, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), R.string.is_empty, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun navigateCamera(googleMap: GoogleMap, latLng: LatLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, UiConstants.ZOOM_STREET))
    }

    private fun setBottomSheetData(query: String) {
        val adapter = BottomSheetRecyclerAdapter(this)
        binding.bottom.rv.adapter = adapter

        lifecycleScope.launch {
            try {
                viewModel.fetchGeoCodeInfo(query).apply {
                    adapter.update(data)
                    binding.bottom.addLocationButton.enable()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), R.string.not_connection, Toast.LENGTH_SHORT).show()
                binding.bottom.addLocationButton.enable()
            }
        }
    }

    private fun setOnCameraChangeListener(googleMap: GoogleMap) {
        googleMap.setOnCameraMoveListener {
            binding.bottom.addLocationButton.disable()
            setBottomSheetData(googleMap.formatToPosition())
        }
    }

    override fun onClickAddLocation(data: Data) {
        lifecycleScope.launch {
            viewModel.addGeoCode(data.mapToDbModel())
            Toast.makeText(requireContext(), R.string.success_save, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        setBottomSheetData("${p0.position.latitude},${p0.position.longitude}")
        return true
    }
}