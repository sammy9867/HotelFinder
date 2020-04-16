package com.thesis.hotelfinder.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.HotelResponse
import com.thesis.hotelfinder.model.Hotel
import com.thesis.hotelfinder.repository.HotelRepository

class HotelsViewModel(context: Context) : ViewModel(){

    private var hotelsRepository : HotelRepository = HotelRepository.getInstance(context)

    fun getHotelsListFromLocationId(location_id: Int, check_in_date: String,
                                    number_of_adults: Int, number_of_rooms :Int,
                                    max_price: Int, hotel_class: Float) : LiveData<Resource<List<Hotel>>> {
        return hotelsRepository.getHotelsListFromLocationId(location_id, check_in_date, number_of_adults, number_of_rooms,
            max_price, hotel_class)
    }


}