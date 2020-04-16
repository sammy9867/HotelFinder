package com.thesis.hotelfinder.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.databinding.FragmentHotelAmenityBinding


class HotelAmenityFragment : Fragment() {


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding: FragmentHotelAmenityBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotel_amenity, container, false
        )

        val location_id = arguments?.getInt("location_id", 0)

        // Navigate back to HotelFilterFragment
        amenityNavigateBack(binding, location_id!!)


        return binding.root
    }

    private fun amenityNavigateBack(binding: FragmentHotelAmenityBinding, getLocationId: Int){
        binding.AmenityDoneBtn.setOnClickListener{
            val bundle = bundleOf("location_id" to getLocationId)
            view!!.findNavController().navigate(R.id.action_hotelAmenityFragment_to_hotelFilterFragment, bundle)
        }
    }

}
