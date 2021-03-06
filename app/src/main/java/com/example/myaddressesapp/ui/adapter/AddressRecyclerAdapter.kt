package com.example.myaddressesapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myaddressesapp.databinding.HistoryItemBinding
import com.example.myaddressesapp.ui.models.AddressUiModel
import com.example.myaddressesapp.utils.copyText

class AddressRecyclerAdapter(private val callback:CallBack): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldList = ArrayList<AddressUiModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<AddressUiModel>){
        oldList.clear()
        oldList.addAll(newList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun notifyChanged(){
        notifyDataSetChanged()
    }

    inner class VH(private val historyItemBinding: HistoryItemBinding):RecyclerView.ViewHolder(historyItemBinding.root){

        @SuppressLint("SetTextI18n")
        fun onBind(addressUiModel: AddressUiModel){
            historyItemBinding.addressName.text = addressUiModel.name
            historyItemBinding.addressLocation.text = "${addressUiModel.latitude},${addressUiModel.longitude}"

            historyItemBinding.seeLocation.setOnClickListener {
                callback.onClickOpenMap(addressUiModel)
            }

            historyItemBinding.addressLocation.setOnClickListener {
                historyItemBinding.addressLocation.copyText()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    fun getItemNameByPosition(position: Int) = oldList[position].name
    fun getItemByPosition(position: Int) = oldList[position]

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder,position: Int) {
        (holder as VH).onBind(oldList[position])
    }

    override fun getItemCount(): Int = oldList.size

    interface CallBack {
        fun onClickOpenMap(addressUiModel: AddressUiModel)
    }
}