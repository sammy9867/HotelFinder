package com.thesis.hotelfinder.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.HotelsResponse
import com.thesis.hotelfinder.repository.HotelsRepository

class HotelsViewModel(context: Context) : ViewModel(){

    private var hotelsRepository : HotelsRepository = HotelsRepository.getInstance(context)

    fun getHotelsListFromLocationId(location_id: Int, check_in_date: String,
                                    number_of_adults: Int, number_of_rooms :Int) : LiveData<Resource<HotelsResponse>> {
        return hotelsRepository.getHotelsListFromLocationId(location_id, check_in_date, number_of_adults, number_of_rooms)
    }

}