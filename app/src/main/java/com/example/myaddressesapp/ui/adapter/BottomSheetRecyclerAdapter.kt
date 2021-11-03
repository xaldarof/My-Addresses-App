package com.example.myaddressesapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myaddressesapp.data.cloud.models.response.map.GeoCoderResponseBody
import com.example.myaddressesapp.databinding.BottomSheetItemLayoutBinding
import com.example.myaddressesapp.utils.copyText

class BottomSheetRecyclerAdapter(private val callback:CallBack): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val oldList = ArrayList<GeoCoderResponseBody>()

    @SuppressLint("NotifyDataSetChanged")
    fun update(newList: ArrayList<GeoCoderResponseBody>){
        oldList.clear()
        oldList.addAll(newList)
        notifyDataSetChanged()
    }


    inner class VH(private val bottomSheetItemLayoutBinding: BottomSheetItemLayoutBinding)
        :RecyclerView.ViewHolder(bottomSheetItemLayoutBinding.root){

        fun onBind(data: GeoCoderResponseBody){

            bottomSheetItemLayoutBinding.addressName.text = data.display_name
            bottomSheetItemLayoutBinding.locationTv.text = data.lat.plus(",${data.lon}")

            bottomSheetItemLayoutBinding.locationTv.setOnClickListener {
                bottomSheetItemLayoutBinding.locationTv.copyText()
            }

            bottomSheetItemLayoutBinding.addLocationBtn.setOnClickListener {
                callback.onClickAddLocation(data)
            }

            bottomSheetItemLayoutBinding.container.setOnClickListener {
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
        fun onClickAddLocation(geoCoderResponseBody: GeoCoderResponseBody)
    }
}