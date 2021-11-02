package com.example.myaddressesapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myaddressesapp.R
import com.example.myaddressesapp.data.cache.AddressCacheRepository
import com.example.myaddressesapp.data.cloud.models.response.map.Data
import com.example.myaddressesapp.databinding.BottomSheetItemLayoutBinding
import com.example.myaddressesapp.utils.copyText
import javax.inject.Inject

class BottomSheetRecyclerAdapter(private val callback:CallBack): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldList = ArrayList<Data>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: List<Data>){
        oldList.clear()
        oldList.addAll(newList)
        notifyDataSetChanged()
    }


    inner class VH(private val bottomSheetItemLayoutBinding: BottomSheetItemLayoutBinding)
        :RecyclerView.ViewHolder(bottomSheetItemLayoutBinding.root){

        fun onBind(data: Data){

            bottomSheetItemLayoutBinding.addressName.text = data.name
            bottomSheetItemLayoutBinding.locationTv.text = data.latitude.toString().plus(",${data.longitude}")

            bottomSheetItemLayoutBinding.locationTv.setOnClickListener {
                bottomSheetItemLayoutBinding.locationTv.copyText()
            }

            bottomSheetItemLayoutBinding.addLocationBtn.setOnClickListener {
                callback.onClickAddLocation(data)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VH(BottomSheetItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VH).onBind(oldList[position])
    }

    override fun getItemCount(): Int = oldList.size

    interface CallBack {
        fun onClickAddLocation(data: Data)
    }
}