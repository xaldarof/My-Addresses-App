package com.example.myaddressesapp.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import com.example.myaddressesapp.data.cloud.models.response.map.Data
import com.example.myaddressesapp.utils.*
import com.example.myaddressesapp.databinding.FragmentMapBinding
import com.example.myaddressesapp.ui.UiConstants
import com.example.myaddressesapp.ui.adapter.BottomSheetRecyclerAdapter
import com.example.myaddressesapp.ui.dialogs.ChangeNameDialog
import com.example.myaddressesapp.ui.dialogs.SelectMapStyleDialog
import com.example.myaddressesapp.ui.models.AddressUiModel
import com.example.myaddressesapp.vm.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback, BottomSheetRecyclerAdapter.CallBack,
    ChangeNameDialog.CallBack, SelectMapStyleDialog.CallBack {

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
            SelectMapStyleDialog.Base(requireContext(), this).show()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        setOnCameraChangeListener(p0)
        p0.isMyLocationEnabled = requireActivity().isLocationPermissionGranted()

        binding.mapView.onResume()
        p0.defineUserSelectedMapStyle(viewModel.fetchUserMapStyle()!!, requireContext())
        googleMap = p0

        lifecycleScope.launch {
            viewModel.fetchUserLastLocation().apply {
                navigateCamera(p0, LatLng(lat, lon))
            }
        }

        binding.zoomMinus.setOnClickListener {
            p0.zoomMinus(1f)
        }

        binding.zoomPlus.setOnClickListener {
            p0.zoomPlus(1f)
        }

        binding.bottom.addLocationButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val currentLocation = p0.formatToPosition()

                    if (currentLocation.isNotEmpty()) {
                        val address = viewModel.fetchSingleCodeInfo(currentLocation).data[0].mapToDbModel()
                        viewModel.addGeoCode(address.mapToUiModel())
                        binding.bottom.addLocationButton.disable()

                        ChangeNameDialog.Base(requireContext()).show(address.mapToUiModel(), this@MapFragment)
                        binding.bottom.addLocationButton.enable()

                    } else {
                        Toast.makeText(requireContext(), R.string.is_empty, Toast.LENGTH_SHORT)
                            .show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT)
                    .show()
                }
            }
        }
    }

    private fun navigateCamera(googleMap: GoogleMap, latLng: LatLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, UiConstants.ZOOM_STREET))
    }

    private fun setOnCameraChangeListener(googleMap: GoogleMap) {
        googleMap.setOnCameraIdleListener {
            lifecycleScope.launch {
                viewModel.saveUserLastLocation(
                    UserLocation(googleMap.cameraPosition.target.latitude,
                        googleMap.cameraPosition.target.longitude))
                setBottomSheetData(googleMap.formatToPosition())
            }
        }
    }


    private suspend fun setBottomSheetData(query: String) {
        val adapter = BottomSheetRecyclerAdapter(this)
        binding.bottom.rv.adapter = adapter

        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    viewModel.fetchGeoCodeInfo(query).apply {
                        adapter.update(data)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), R.string.error, Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onClickAddLocation(data: Data) {
        ChangeNameDialog.Base(requireContext()).show(data.mapToUiModel(), this@MapFragment)
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
}