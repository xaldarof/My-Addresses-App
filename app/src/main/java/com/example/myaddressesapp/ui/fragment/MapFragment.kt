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
import androidx.navigation.fragment.navArgs
import com.example.myaddressesapp.R
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import com.example.myaddressesapp.data.cloud.models.request.Address
import com.example.myaddressesapp.data.cloud.models.request.AddressRequestBody
import com.example.myaddressesapp.data.cloud.models.response.map.Data
import com.example.myaddressesapp.data.utils.bellAnimation
import com.example.myaddressesapp.data.utils.formatToPosition
import com.example.myaddressesapp.data.utils.isLocationPermissionGranted
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
            findNavController()
                .navigate(MapFragmentDirections.actionMapFragmentToHistoryFragment())
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap) {
        setOnCameraChangeListener(p0)
        p0.isMyLocationEnabled = requireActivity().isLocationPermissionGranted()
        binding.mapView.onResume()

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<AddressModelDb>(UiConstants.ADDRESS_ARG)
            ?.observe(viewLifecycleOwner,{
                setBottomSheetData("${it.latitude},${it.longitude}")
                navigateCamera(p0,LatLng(it.latitude,it.longitude))
            })
    }

    private fun  navigateCamera(googleMap: GoogleMap,latLng: LatLng){
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, UiConstants.ZOOM_STREET))
    }


    private fun setBottomSheetData(query: String) {
        val adapter = BottomSheetRecyclerAdapter(this)
        binding.bottom.rv.adapter = adapter

        lifecycleScope.launch {
            try {
            viewModel.fetchGeoCodeInfo(query).apply {
                adapter.update(data)
                binding.bottom.addLocationButton.text = UiConstants.ADD_TO_HISTORY
                binding.locationImg.bellAnimation()
            }
        }catch (e:Exception){
                Toast.makeText(requireContext(), R.string.not_connection, Toast.LENGTH_SHORT).show()
                binding.bottom.addLocationButton.text = UiConstants.ADD_TO_HISTORY
            }
        }
    }

    private fun setOnCameraChangeListener(googleMap: GoogleMap) {
        googleMap.setOnCameraMoveListener {
            binding.bottom.addLocationButton.text = UiConstants.DOWNLOADING
            setBottomSheetData(googleMap.formatToPosition())
        }
    }

    override fun onClickAddLocation(data: Data) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = viewModel.createAddress(AddressRequestBody(data.label, Address(data.name, data.latitude,
                    data.longitude), data.type, UiConstants.EMPTY))
                    viewModel.addGeoCode(response.data.mapToDbModel())
                }
                catch (e:Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        setBottomSheetData("${p0.position.latitude},${p0.position.longitude}")
        return true
    }
}