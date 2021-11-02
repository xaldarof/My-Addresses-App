package com.example.myaddressesapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myaddressesapp.R
import com.example.myaddressesapp.databinding.MapItemLayoutBinding
import com.example.myaddressesapp.ui.UiConstants

@SuppressLint("NotifyDataSetChanged")
class SelectMapStyleAdapter(private val callback:CallBack): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var oldList = ArrayList<String>()

    init {
        oldList.add("Normal")
        oldList.add("Hybrid")
        oldList.add("Satellite")
        oldList.add("Night")
        notifyDataSetChanged()
    }

    inner class VH(private val binding:MapItemLayoutBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun onBind(name:String){
            binding.mapName.text = name

            binding.selectMapBtn.setOnClickListener {
                callback.onSelectLocation(name)
            }

            if (name == UiConstants.NORMAL_MAP) {
                binding.mapIcon.setImageResource(R.drawable.normal_map)
            }
            if (name == UiConstants.HYBRID_MAP) {
                binding.mapIcon.setImageResource(R.drawable.hybrid)
            }
            if (name == UiConstants.SATELLITE_MAP){
                binding.mapIcon.setImageResource(R.drawable.satellite)
            }
            if (name == UiConstants.DARK_MAP) {
                binding.mapIcon.setImageResource(R.drawable.night)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(MapItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VH).onBind(oldList[position])
    }

    override fun getItemCount(): Int = oldList.size

    interface CallBack {
        fun onSelectLocation(name: String)
    }
}