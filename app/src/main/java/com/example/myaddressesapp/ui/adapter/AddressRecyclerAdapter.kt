package com.example.myaddressesapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myaddressesapp.data.cache.models.AddressModelDb
import com.example.myaddressesapp.databinding.HistoryItemBinding

class AddressRecyclerAdapter(private val callback:CallBack): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldList = ArrayList<AddressModelDb>()

    fun update(newList: List<AddressModelDb>){
        oldList.clear()
        oldList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class VH(private val historyItemBinding: HistoryItemBinding):RecyclerView.ViewHolder(historyItemBinding.root){

        fun onBind(addressModelDb: AddressModelDb){
            historyItemBinding.addressName.text = addressModelDb.name
            historyItemBinding.locationTv.text = addressModelDb.latitude.toString().plus(addressModelDb.longitude.toString())

            historyItemBinding.seeLocationBtn.setOnClickListener {
                callback.onClickOpenMap(addressModelDb)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VH).onBind(oldList[position])
    }

    override fun getItemCount(): Int = oldList.size

    interface CallBack {
        fun onClickOpenMap(addressModelDb: AddressModelDb)
    }
}