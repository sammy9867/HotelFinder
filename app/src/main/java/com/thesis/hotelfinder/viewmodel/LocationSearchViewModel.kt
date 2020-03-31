package com.thesis.hotelfinder.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.LocationSearchResponse
import com.thesis.hotelfinder.repository.LocationSearchRepository

class LocationSearchViewModel(context: Context) :ViewModel(){

    private var locationSearchRepository : LocationSearchRepository = LocationSearchRepository.getInstance(context)

    fun getLocationIdFromLocationSearch(query: String, currency: String) : LiveData<Resource<LocationSearchResponse>>{
        return locationSearchRepository.getLocationIdFromLocationSearch(query, currency)
    }

}