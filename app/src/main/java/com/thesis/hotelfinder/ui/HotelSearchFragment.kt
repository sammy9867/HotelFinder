package com.thesis.hotelfinder.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.databinding.FragmentHotelSearchBinding


class HotelSearchFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding: FragmentHotelSearchBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotel_search, container, false
        )

        return binding.root
    }


}
