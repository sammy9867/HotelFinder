package com.thesis.hotelfinder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator
import com.thesis.hotelfinder.api.response.HotelsResponse

class HotelsRepository(context: Context){

    companion object {
        private var instance : HotelsRepository? = null
        fun getInstance(context : Context) : HotelsRepository {
            if (instance == null) {
                instance = HotelsRepository(context)
            }

            return instance as HotelsRepository
        }
    }

    fun getHotelsListFromLocationId(location_id: Int, check_in_date: String,
                                    number_of_adults: Int, number_of_rooms :Int): LiveData<Resource<HotelsResponse>> {

        return object : NetworkBoundResource<HotelsResponse>() {
            override fun createCall(): LiveData<Resource<HotelsResponse>> {
                return ServiceGenerator.apiService.
                    getHotelsListFromLocationId(location_id, check_in_date, number_of_adults, number_of_rooms)
            }

        }.asLiveData()
    }
}