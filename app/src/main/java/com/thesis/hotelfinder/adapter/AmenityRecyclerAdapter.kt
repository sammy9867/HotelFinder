package com.thesis.hotelfinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.model.AmenityFilter
import com.thesis.hotelfinder.model.Hotel
import kotlinx.android.synthetic.main.layout_amenities.view.*
import kotlinx.android.synthetic.main.layout_hotel_items.view.*


class AmenityRecyclerAdapter(private val context: Context, private val amenityList: MutableList<AmenityFilter>) :
    RecyclerView.Adapter<AmenityRecyclerAdapter.AmenityListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenityListViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.layout_amenities, parent, false)
        return AmenityListViewHolder(view)
    }

    override fun onBindViewHolder(holder: AmenityListViewHolder, position: Int) {
        val amenity = amenityList[position]
        Glide.with(this.context)
            .load(amenity.icon)
            .apply( RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(holder.amenityIv)
        holder.amenityTv.text = amenity.name
    }

    override fun getItemCount(): Int {
        return amenityList.size
    }

    inner class AmenityListViewHolder(itemView: View):
        RecyclerView.ViewHolder(itemView){

        val amenityIv = itemView.amenityIv
        val amenityTv =  itemView.amenityTv
    }
}



