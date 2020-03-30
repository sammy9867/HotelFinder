package com.thesis.hotelfinder.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.HotelsResponse
import com.thesis.hotelfinder.repository.HotelsRepository

class HotelsViewModel(context: Context) :ViewModel(){

    private var hotelsRepository : HotelsRepository = HotelsRepository.getInstance(context)

    fun getHotelsFromLocationSearch(query: String, currency: String) : LiveData<Resource<HotelsResponse>>{
        return hotelsRepository.getHotelsFromLocationSearch(query, currency)
    }

}