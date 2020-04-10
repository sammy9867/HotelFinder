package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.gson.GsonBuilder

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.HotelDetailsResponse
import com.thesis.hotelfinder.databinding.FragmentHotelDetailsBinding
import com.thesis.hotelfinder.model.HotelDetails
import com.thesis.hotelfinder.viewmodel.HotelDetailsViewModel
import com.thesis.hotelfinder.viewmodel.HotelDetailsViewModelFactory


class HotelDetailsFragment : Fragment() {

    private lateinit var hotelDetailsViewModel: HotelDetailsViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding : FragmentHotelDetailsBinding =DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotel_details, container, false
        )

        val getLocationId = arguments?.getInt("hotel_location_id", 0)
        Log.i("HotelBundle", getLocationId.toString())

        hotelDetailsViewModel =  ViewModelProviders.of(this, HotelDetailsViewModelFactory(context!!)).
            get(HotelDetailsViewModel::class.java)
        hotelDetailsViewModel.getHotelsListFromLocationId(getLocationId!!).
            observe(this, Observer<Resource<HotelDetails>>{ hotelDetailsResponse ->

                when{
                    hotelDetailsResponse.status.isLoading() ->{

                    }

                    hotelDetailsResponse.status.isSuccessful() -> {

                        Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show()
                        val response = hotelDetailsResponse.data

                        if(response != null){

                            Glide.with(this)
                                .load(response.photo!!.images.original.url)
                                .apply( RequestOptions()
                                    .diskCacheStrategy(DiskCacheStrategy.ALL))
                                .into(binding.hotelDetailsIv)

                            binding.hotelDetailsName.text = response.name
                            binding.hotelDetailsNumReviews.text = response.num_reviews.toString()
                            binding.hotelDetailsRating.text = response.rating.toString()
                            binding.hotelDetailsRanking.text = response.ranking
                            binding.hotelDetailsAddress.text = response.address
                            binding.hotelDetailsPhone.text = response.phone
                        }

                    }

                    hotelDetailsResponse.status.isError() ->{
                        Toast.makeText(context, "isError", Toast.LENGTH_SHORT).show()
                        Log.i("error", GsonBuilder().setPrettyPrinting().create().toJson(hotelDetailsResponse.errorMessage))
                    }
                }

            })

        return binding.root
    }

}
