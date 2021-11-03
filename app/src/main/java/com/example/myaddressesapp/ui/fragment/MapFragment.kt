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
import com.example.myaddressesapp.data.cache.models.UserLocation
import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseBody
import com.example.myaddressesapp.utils.*
import com.example.myaddressesapp.databinding.FragmentMapBinding
import com.example.myaddressesapp.ui.UiConstants
import com.example.myaddressesapp.ui.adapter.BottomSheetRecyclerAdapter
import com.example.myaddressesapp.ui.dialogs.AddGeoCodeLocationDialog
import com.example.myaddressesapp.ui.dialogs.AddLocationDialog
import com.example.myaddressesapp.ui.dialogs.SelectMapStyleDialog
import com.example.myaddressesapp.ui.models.AddressUiModel
import com.example.myaddressesapp.vm.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, BottomSheetRecyclerAdapter.CallBack,
    AddLocationDialog.CallBack, SelectMapStyleDialog.CallBack,AddGeoCodeLocationDialog.CallBack {

    private lateinit var binding: FragmentMapBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.bottom.bottomContainer.collapse()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        binding.historyBtn.setOnClickListener {
            findNavController().navigate(MapFragmentDirections.actionMapFragmentToHistoryFragment())
        }

        binding.selectMap.setOnClickListener {
            SelectMapStyleDialog.Base(requireContext(), this,viewModel.fetchUserMapStyle()!!).show()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        setOnCameraChangeListener(p0)
        binding.mapView.onResume()
        p0.defineUserSelectedMapStyle(viewModel.fetchUserMapStyle()!!, requireContext())


        lifecycleScope.launch {
            viewModel.fetchUserLastLocation().apply {
                navigateCamera(p0, LatLng(lat, lon))
            }
        }

        binding.zoomMinus.setOnClickListener { p0.zoomMinus(1f) }

        binding.zoomPlus.setOnClickListener { p0.zoomPlus(1f) }

        binding.bottom.addLocationButton.setOnClickListener { showAddLocationDialog(p0) }

        binding.myLocationBtn.setOnClickListener { navigateToUserLocation(p0) }
    }

    private fun showAddLocationDialog(p0: GoogleMap) {
        lifecycleScope.launch {
            val lat = p0.cameraPosition.target.latitude
            val lon = p0.cameraPosition.target.longitude
            AddLocationDialog.Base(requireContext()).show(lat.toString(),lon.toString(), this@MapFragment)
        }
    }

    @SuppressLint("MissingPermission", "VisibleForTests")
    private fun navigateToUserLocation(p0: GoogleMap){
        if (requireActivity().isLocationPermissionGranted()) {
            FusedLocationProviderClient(requireActivity()).lastLocation.addOnSuccessListener {
                navigateCamera(p0,LatLng(it.latitude,it.longitude))
            }
        } else requireActivity().requestLocationPermission()
    }


    private fun navigateCamera(googleMap: GoogleMap, latLng: LatLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, viewModel.fetchUserLastZoom()))
    }

    private fun setOnCameraChangeListener(googleMap: GoogleMap) {
        googleMap.setOnCameraIdleListener {
            lifecycleScope.launch {
                setBottomSheetData(googleMap.cameraPosition.target.latitude,googleMap.cameraPosition.target.longitude)
                viewModel.saveUserLastZoom(googleMap.cameraPosition.zoom)
            }
        }
    }

    private suspend fun setBottomSheetData(lat:Double,lon:Double) {
        val adapter = BottomSheetRecyclerAdapter(this)
        binding.bottom.rv.adapter = adapter

        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                try {
                    viewModel.fetchGeoCodeInfo(lat,lon).apply {
                        adapter.update(arrayListOf(this))
                    }
                } catch (e:Exception){
                    Toast.makeText(requireContext(), R.string.time_out, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onClickAddLocation(geoCoderResponseBody: GeoCoderResponseBody) {
        AddGeoCodeLocationDialog(this,geoCoderResponseBody).show(parentFragmentManager,UiConstants.EMPTY)
    }

    override fun onClickSave(uiModel: AddressUiModel) {
        lifecycleScope.launch {
            viewModel.addGeoCode(uiModel)
        }
    }

    override fun onSelectMapStyle(name: String) {
        viewModel.saveUserMapStyle(name)
        googleMap.defineUserSelectedMapStyle(name, requireContext())
    }

    override fun onClickSaveGeoCodeLocation(uiModel: AddressUiModel) {
        lifecycleScope.launch {
            viewModel.addGeoCode(uiModel)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveUserLastLocation(
            UserLocation(googleMap.cameraPosition.target.latitude, googleMap.cameraPosition.target.longitude))
    }
}