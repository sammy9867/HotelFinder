package com.thesis.hotelfinder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator
import com.thesis.hotelfinder.api.response.HotelDetailsResponse

class HotelDetailsRepository(context: Context){

    companion object {
        private var instance : HotelDetailsRepository? = null
        fun getInstance(context : Context) : HotelDetailsRepository {
            if (instance == null) {
                instance = HotelDetailsRepository(context)
            }

            return instance as HotelDetailsRepository
        }
    }

    fun getHotelDetailsListFromLocationId(location_id: Int): LiveData<Resource<HotelDetailsResponse>> {

        return object : NetworkBoundResource<HotelDetailsResponse>() {
            override fun createCall(): LiveData<Resource<HotelDetailsResponse>> {
                return ServiceGenerator.tripAdvisorApiService.
                    getHotelDetailsListFromLocationId(location_id)
            }

        }.asLiveData()
    }
}