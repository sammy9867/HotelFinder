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
import com.thesis.hotelfinder.model.Hotel
import kotlinx.android.synthetic.main.layout_hotel_items.view.*


class HotelRecyclerAdapter(private val context: Context, private val hotelList: MutableList<Hotel>,
                           private val onHotelListener: OnHotelListener) :
    RecyclerView.Adapter<HotelRecyclerAdapter.HotelListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelListViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.layout_hotel_items, parent, false)
        return HotelListViewHolder(view, onHotelListener)
    }

    override fun onBindViewHolder(holder: HotelListViewHolder, position: Int) {
        val hotel = hotelList[position]
        holder.hotelNameTv.text = hotel.name
        holder.hotelRatingsTv.text = hotel.rating.toString()
        Glide.with(this.context)
            .load(hotel.photo!!.images.original.url)
            .apply( RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(holder.hotelIv)
    }

    override fun getItemCount(): Int {
        return hotelList.size
    }

    inner class HotelListViewHolder(itemView: View, private val onHotelListener: OnHotelListener):
        RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val hotelNameTv =  itemView.hotelName
        val hotelRatingsTv = itemView.hotelRating
        val hotelIv = itemView.hotelImageView

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v : View?) {
            onHotelListener.onHotelClick(adapterPosition)
        }
    }
}



