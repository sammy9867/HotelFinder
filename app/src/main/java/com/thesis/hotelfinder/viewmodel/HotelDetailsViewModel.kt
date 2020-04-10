package com.thesis.hotelfinder.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.HotelDetailsResponse
import com.thesis.hotelfinder.api.response.HotelResponse
import com.thesis.hotelfinder.model.HotelDetails
import com.thesis.hotelfinder.repository.HotelDetailsRepository
import com.thesis.hotelfinder.repository.HotelRepository

class HotelDetailsViewModel(context: Context) : ViewModel(){

    private var hotelDetailsRepository : HotelDetailsRepository = HotelDetailsRepository.getInstance(context)

    fun getHotelsListFromLocationId(location_id: Int) : LiveData<Resource<HotelDetails>> {
        return hotelDetailsRepository.getHotelDetailsListFromLocationId(location_id)
    }

}