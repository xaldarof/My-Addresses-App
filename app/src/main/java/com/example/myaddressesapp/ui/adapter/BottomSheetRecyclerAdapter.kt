package com.example.myaddressesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myaddressesapp.data.cloud.models.response.map.Data
import com.example.myaddressesapp.databinding.HistoryItemBinding

class BottomSheetRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldList = ArrayList<Data>()

    fun update(newList: List<Data>){
        oldList.clear()
        oldList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class VH(private val historyItemBinding: HistoryItemBinding):RecyclerView.ViewHolder(historyItemBinding.root){

        fun onBind(data: Data){
            historyItemBinding.addressName.text = data.name
            historyItemBinding.locationTv.text = data.latitude.toString().plus(data.longitude.toString())

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VH).onBind(oldList[position])
    }

    override fun getItemCount(): Int = oldList.size
}