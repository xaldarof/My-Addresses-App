package com.example.myaddressesapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myaddressesapp.R
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import com.example.myaddressesapp.databinding.FragmentHistoryBinding
import com.example.myaddressesapp.ui.adapter.AddressRecyclerAdapter
import com.example.myaddressesapp.vm.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private lateinit var binding:FragmentHistoryBinding
    private val viewModel:HistoryViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = AddressRecyclerAdapter()
        binding.rv.adapter = adapter

        lifecycleScope.launch {
            viewModel.fetchAddresses().collectLatest {
                adapter.update(it)
            }
        }

        for (i in 0 until 30) {
            viewModel.addAddress(AddressModelDb("Ulugbek Kochasi $i",1.21212,121.2121))
        }
    }
}