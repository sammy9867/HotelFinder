package com.thesis.hotelfinder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator.tripAdvisorApiService
import com.thesis.hotelfinder.api.network.ServiceGenerator.unSplashApiService
import com.thesis.hotelfinder.api.response.LocationSearchResponse
import com.thesis.hotelfinder.api.response.UnSplashPhotosResponse


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
                return tripAdvisorApiService.getLocationIdFromLocationSearch(query, currency)
            }

        }.asLiveData()
    }

    fun getPhotos(query: String): LiveData<Resource<UnSplashPhotosResponse>>{

        return object : NetworkBoundResource<UnSplashPhotosResponse>() {
            override fun createCall(): LiveData<Resource<UnSplashPhotosResponse>> {
                return unSplashApiService.getPhotos(query)
            }

        }.asLiveData()
    }



}