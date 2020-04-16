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
import com.thesis.hotelfinder.databinding.FragmentHotelFilterBinding
import com.thesis.hotelfinder.model.HotelFilter


class HotelFilterFragment : Fragment() {

    lateinit var hotelFilter: HotelFilter

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding: FragmentHotelFilterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotel_filter, container, false
        )

        // Apply Default Filter
        defaultFilter()

        val location_id = arguments?.getInt("location_id", 0)
        // Apply Filters
        applyFilters(binding, location_id!!)

        // Reset Filters
        resetFilters(binding)

        // Navigate back to HotelFragment
        filterNavigateBack(binding, location_id)

        // Navigate to HotelAmenityFragment
        filterNavigateToAmenities(binding, location_id)

        return binding.root
    }

    private fun applyFilters(binding: FragmentHotelFilterBinding, location_id: Int){
        binding.filterApplyBtn.setOnClickListener{
            // Get Filters from user
            getAppliedFilters(binding)
            bundlesForHotelFragment(location_id)
        }
    }

    private fun resetFilters(binding: FragmentHotelFilterBinding){
        binding.filterResetBtn.setOnClickListener{
            Log.i("onClick", "Reset")
        }
    }

    private fun defaultFilter(){
        hotelFilter  = HotelFilter(30, 3.0f)
    }

    private fun getAppliedFilters(binding: FragmentHotelFilterBinding){

        val max_price = binding.filterPriceRange.maximumValue.toInt()
        val hotel_class = binding.filterHotelClass.rating

        Log.i("HotelFilter", ""+max_price + " " + hotel_class)
        hotelFilter = HotelFilter( max_price, hotel_class)
    }

    private fun filterNavigateBack(binding: FragmentHotelFilterBinding, location_id: Int){
        binding.hotelFilterBackIb.setOnClickListener{
            val bundle = bundleOf("location_id" to location_id)
            view!!.findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelFragment, bundle)
        }
    }

    private fun filterNavigateToAmenities(binding: FragmentHotelFilterBinding, location_id: Int){
        binding.filterAmenitiesBtn.setOnClickListener{
            bundlesForHotelAmenityFragment(location_id)
        }
    }


    /**Bundles**/
    private fun bundlesForHotelFragment(location_id: Int){
        val bundle = bundleOf("hotel_filter" to hotelFilter)
        bundle.putInt("location_id", location_id)
        view!!.findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelFragment, bundle)
    }

    private fun bundlesForHotelAmenityFragment(location_id: Int){
        val bundle = bundleOf("location_id" to location_id)
        view!!.findNavController().navigate(R.id.action_hotelFilterFragment_to_hotelAmenityFragment, bundle)
    }


}
