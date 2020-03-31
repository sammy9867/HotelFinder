package com.thesis.hotelfinder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator.apiService
import com.thesis.hotelfinder.api.response.LocationSearchResponse


class LocationSearchRepository(context: Context){

    companion object {
        private var instance : LocationSearchRepository? = null
        fun getInstance(context : Context) : LocationSearchRepository {
            if (instance == null) {
                instance = LocationSearchRepository(context)
            }

            return instance as LocationSearchRepository
        }
    }

    fun getLocationIdFromLocationSearch
                (query: String, currency: String): LiveData<Resource<LocationSearchResponse>>{

        return object : NetworkBoundResource<LocationSearchResponse>() {
            override fun createCall(): LiveData<Resource<LocationSearchResponse>> {
                return apiService.getLocationIdFromLocationSearch(query, currency)
            }

        }.asLiveData()
    }
}