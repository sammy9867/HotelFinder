package com.thesis.hotelfinder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.model.Country
import kotlinx.android.synthetic.main.layout_grid_images.view.*

class StaggeredRecyclerAdapter(private val context: Context,
                               private val countryList: MutableList<Country>,
                               private val onCountryListener: OnCountryListener):
        RecyclerView.Adapter<StaggeredRecyclerAdapter.StaggeredViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaggeredViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.layout_grid_images, parent, false)
        return StaggeredViewHolder(view, onCountryListener)
    }

    override fun onBindViewHolder(holder: StaggeredViewHolder, position: Int) {
        val country = countryList[position]

        Glide.with(this.context)
            .load(country.image_url)
            .apply( RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(holder.countryImage)

        holder.countryName.text = country.name
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    inner class StaggeredViewHolder(itemView : View, private val onCountryListener: OnCountryListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val countryImage = itemView.countryImageView
        val countryName = itemView.countryTextView

        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v : View?) {
            onCountryListener.onCountryClick(adapterPosition)
        }
    }
}