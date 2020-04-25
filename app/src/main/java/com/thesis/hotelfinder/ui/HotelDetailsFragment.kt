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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.adapter.AmenityRecyclerAdapter
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.databinding.FragmentHotelDetailsBinding
import com.thesis.hotelfinder.model.AmenityFilter
import com.thesis.hotelfinder.model.HotelDetails
import com.thesis.hotelfinder.util.AmenityFilterData
import com.thesis.hotelfinder.util.Constants
import com.thesis.hotelfinder.viewmodel.HotelDetailsViewModel
import com.thesis.hotelfinder.viewmodel.MyViewModelFactory


class HotelDetailsFragment : Fragment() {

    private lateinit var binding: FragmentHotelDetailsBinding

    private lateinit var hotelDetailsViewModel: HotelDetailsViewModel
    private val amenityFilterList = AmenityFilterData().getAmenityFilterList()

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val dataBinding : FragmentHotelDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotel_details, container, false
        )
        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        val hotelLocationId = arguments?.getInt("hotel_location_id")
        Log.i("HotelBundle", hotelLocationId.toString())

        filterNavigateBack(binding)

        hotelDetailsViewModel =  ViewModelProviders.of(this, MyViewModelFactory(context!!)).
            get(HotelDetailsViewModel::class.java)
        hotelDetailsViewModel.getHotelsListFromLocationId(hotelLocationId!!).
            observe(this, Observer<Resource<HotelDetails>>{ hotelDetailsResponse ->

                if(hotelDetailsResponse?.data != null){
                    Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show()
                    val response = hotelDetailsResponse.data
                    Glide.with(this)
                        .load(response.photo!!.images.original.url)
                        .apply( RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(binding.hotelDetailsIv)

                    binding.hotelDetailsName.text = response.name
                    binding.hotelDetailsAddress.text = response.address
                    binding.hotelDetailsPhone.text = response.phone
                    binding.hotelDetailsRanking.text = response.ranking
                    binding.hotelDetailsRating.text = response.rating.toString()
                    binding.hotelDetailsNumReviews.text = "("  +response.num_reviews.toString() + " reviews)"
                    binding.hotelDetailsDescription.text = response.description

                    val amenityList = mutableListOf<AmenityFilter>()
                    val adapter = AmenityRecyclerAdapter(context!!, amenityList)
                    binding.rvAmenity.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvAmenity.adapter = adapter

                    for(amenityFilter in amenityFilterList){
                        for(amenityName in response.amenities){
                            if(amenityFilter.id == amenityName.key){
                                amenityList.add(amenityFilter)
                                Log.i("Amenity", amenityFilter.name + " " + amenityFilter.icon.toString())
                            }
                        }
                    }

                    binding.rvAmenity.adapter!!.notifyDataSetChanged()
                }else{
                    Log.i("error", "HotelDetails")
                }

            })

    }

    private fun filterNavigateBack(binding: FragmentHotelDetailsBinding){
        binding.hotelDetailsBackIb.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_hotelDetailsFragment_to_hotelFragment)
        }
    }


}
