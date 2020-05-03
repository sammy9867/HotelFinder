package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.checkbox.MaterialCheckBox

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.databinding.FragmentHotelAmenityBinding
import com.thesis.hotelfinder.viewmodel.SharedViewModel


class HotelAmenityFragment : Fragment() {

    private lateinit var binding: FragmentHotelAmenityBinding
    private lateinit var sharedViewModel: SharedViewModel

    private var MaterialCheckBox.checked: Boolean
        get() = isChecked
        set(value) {
            if(isChecked != value) {
                isChecked = value
                jumpDrawablesToCurrentState()
            }

        }

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

        sharedViewModel =  ViewModelProviders.of(activity!!).get(SharedViewModel::class.java)

        // Persist checkboxes state between fragments
        persistCheckBoxes()

        // Navigate back to HotelFilterFragment
        amenityNavigateBack()
    }

    private fun persistCheckBoxes(){

        if(sharedViewModel.amenities.value != null){
            val amenityString = sharedViewModel.amenities.value
            val amenities: List<String> = amenityString!!.split(",").map { it.trim() }
            amenities.forEach { amenity->
                if(amenity == "free_internet") binding.freeInternet.checked = true
                else if(amenity == "free_parking") binding.freeParking.checked = true
                else if(amenity == "room_service") binding.roomService.checked = true
                else if(amenity == "fitness_center") binding.fitnessCenter.checked = true
                else if(amenity == "swimming_pool") binding.swimmingPool.checked = true
                else if(amenity == "pets_allowed") binding.petsAllowed.checked = true
                else if(amenity == "casino") binding.casino.checked = true
                else if(amenity == "restaurant") binding.restaurant.checked = true
                else if(amenity == "laundry") binding.laundry.checked = true
                else if(amenity == "spa") binding.spa.checked = true
                else if(amenity == "non_smoking_hotel") binding.nonSmokingHotel.checked = true
            }
        }
    }

    private fun checkAmenities(): String{

        val amenities = mutableListOf<String>()
        if(binding.freeInternet.checked)amenities.add("free_internet")
        if(binding.freeParking.checked) amenities.add( "free_parking")
        if(binding.roomService.checked) amenities.add( "room_service")
        if(binding.fitnessCenter.checked) amenities.add( "fitness_center")
        if(binding.swimmingPool.checked) amenities.add( "swimming_pool")
        if(binding.petsAllowed.checked)amenities.add( "pets_allowed")
        if(binding.casino.checked)amenities.add( "casino")
        if(binding.restaurant.checked) amenities.add( "restaurant")
        if(binding.laundry.checked) amenities.add( "laundry")
        if(binding.spa.checked) amenities.add( "spa")
        if(binding.nonSmokingHotel.checked) amenities.add( "non_smoking_hotel")

        return amenities.joinToString(",")
    }

    private fun amenityNavigateBack(){
        binding.AmenityDoneBtn.setOnClickListener{
            val amenities = checkAmenities()
            sharedViewModel.setAmenities(amenities)
            view!!.findNavController().navigate(R.id.action_hotelAmenityFragment_to_hotelFilterFragment)
        }
    }

}
