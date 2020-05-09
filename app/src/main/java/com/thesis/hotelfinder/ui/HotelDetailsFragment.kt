package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
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


    private val args: HotelDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

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

        hotelDetailsViewModel =  ViewModelProviders.of(this, MyViewModelFactory(context!!)).
            get(HotelDetailsViewModel::class.java)


        val hotelLocationId = args.hotelLocationId
        val hotelIv = args.hotelImage
        val hotelTv = args.hotelName

        if(hotelIv != null && hotelTv != null){
            binding.hotelDetailsIv.apply {
                transitionName = hotelIv
                Glide.with(this)
                    .load(hotelIv)
                    .apply( RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                    .into(this)
            }

            binding.hotelDetailsName.apply {
                transitionName = hotelTv
                text = hotelTv
            }
        }

        showHotelDetails(hotelLocationId)


                    // Navigate back to HotelFilters
        filterNavigateBack(binding)
    }

    private fun showHotelDetails(hotelLocationId: Int){

        hotelDetailsViewModel.getHotelsListFromLocationId(hotelLocationId).
            observe(this, Observer<Resource<HotelDetails>>{ hotelDetailsResponse ->


                if(hotelDetailsResponse?.data != null){
                    binding.progressBarHotelDetails.hide()
                    binding.addressIcon.visibility = View.VISIBLE
                                        binding.phoneIcon.visibility = View.VISIBLE
                    binding.rankingIcon.visibility = View.VISIBLE
                    binding.hotelDetailsSeparator.visibility = View.VISIBLE

                    Toast.makeText(context, "isSuccessful", Toast.LENGTH_SHORT).show()
                    val response = hotelDetailsResponse.data

                    binding.hotelDetailsAddress.text = response.address
                    binding.hotelDetailsPhone.text = response.phone
                    binding.hotelDetailsRanking.text = response.ranking
                    if(response.price != null){
                        binding.hotelDetailsPrice.text = response.price
                        binding.perNighttv.text = "per night"
                    }
                    binding.hotelDetailsDescription.text = response.description

                    val amenityList = mutableListOf<AmenityFilter>()
                    val adapter = AmenityRecyclerAdapter(context!!, amenityList)
                    binding.rvAmenity.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    binding.rvAmenity.adapter = adapter

                    for(amenityFilter in amenityFilterList){
                        for(amenityName in response.amenities){
                            if(amenityFilter.id == amenityName.key){
                                amenityList.add(amenityFilter)
                            }
                        }
                    }

                    binding.rvAmenity.adapter!!.notifyDataSetChanged()
                }else{
                    binding.progressBarHotelDetails.show()
                    binding.addressIcon.visibility = View.GONE
                    binding.phoneIcon.visibility = View.GONE
                    binding.rankingIcon.visibility = View.GONE
                    binding.hotelDetailsSeparator.visibility = View.GONE
                }

            })
    }

    private fun filterNavigateBack(binding: FragmentHotelDetailsBinding){
        binding.hotelDetailsBackIb.setOnClickListener{
            view!!.findNavController().navigate(R.id.action_hotelDetailsFragment_to_hotelFragment)
        }
    }


}
