package com.thesis.hotelfinder.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.databinding.FragmentHotelDetailsBinding


class HotelDetailsFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding : FragmentHotelDetailsBinding =DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotel_details, container, false
        )

        val getLocationId = arguments?.getInt("hotel_location_id", 0)
        Log.i("HotelBundle", getLocationId.toString())

        return binding.root
    }


}
