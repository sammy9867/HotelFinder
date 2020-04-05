package com.thesis.hotelfinder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator
import com.thesis.hotelfinder.api.response.HotelResponse

class HotelRepository(context: Context){

    companion object {
        private var instance : HotelRepository? = null
        fun getInstance(context : Context) : HotelRepository {
            if (instance == null) {
                instance = HotelRepository(context)
            }

            return instance as HotelRepository
        }
    }

    fun getHotelsListFromLocationId(location_id: Int, check_in_date: String,
                                    number_of_adults: Int, number_of_rooms :Int): LiveData<Resource<HotelResponse>> {

        return object : NetworkBoundResource<HotelResponse>() {
            override fun createCall(): LiveData<Resource<HotelResponse>> {
                return ServiceGenerator.tripAdvisorApiService.
                    getHotelsListFromLocationId(location_id, check_in_date, number_of_adults, number_of_rooms)
            }

        }.asLiveData()
    }
}