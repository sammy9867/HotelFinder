package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.util.Log
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


    private lateinit var binding: FragmentHotelAmenityBinding

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // binding object that holds all views in the given fragment
        val dataBinding: FragmentHotelAmenityBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotel_amenity, container, false
        )
        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.lifecycleOwner = viewLifecycleOwner

        val location_id = arguments?.getInt("location_id", 0)

        // Navigate back to HotelFilterFragment
        amenityNavigateBack(binding, location_id!!)
    }

    private fun getAmenities(binding: FragmentHotelAmenityBinding): String{
        val amenities = mutableListOf<String>()

        if(binding.freeInternet.isChecked)amenities.add("free_internet")
        if(binding.freeParking.isChecked) amenities.add( "free_parking")
        if(binding.roomService.isChecked) amenities.add( "room_service")
        if(binding.fitnessCenter.isChecked) amenities.add( "fitness_center")
        if(binding.swimmingPool.isChecked) amenities.add( "swimming_pool")
        if(binding.petsAllowed.isChecked)amenities.add( "pets_allowed")
        if(binding.casino.isChecked)amenities.add( "casino")
        if(binding.restaurant.isChecked) amenities.add( "restaurant")
        if(binding.laundry.isChecked) amenities.add( "laundry")
        if(binding.spa.isChecked) amenities.add( "spa")
        if(binding.nonSmokingHotel.isChecked) amenities.add( "non_smoking_hotel")

        return amenities.joinToString(",")
    }

    private fun amenityNavigateBack(binding: FragmentHotelAmenityBinding, getLocationId: Int){

        binding.AmenityDoneBtn.setOnClickListener{
            val amenities = getAmenities(binding)
            Log.i("Amen", amenities)
            val bundle = bundleOf("location_id" to getLocationId)
            bundle.putString("amenities", amenities)
            view!!.findNavController().navigate(R.id.action_hotelAmenityFragment_to_hotelFilterFragment, bundle)
        }
    }

}
