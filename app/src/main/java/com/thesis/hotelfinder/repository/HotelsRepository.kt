package com.thesis.hotelfinder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.api.ApiServices
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator.apiService
import com.thesis.hotelfinder.api.response.HotelsResponse
import java.util.*


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

    fun getHotelsFromLocationSearch
                (query: String, currency: String): LiveData<Resource<HotelsResponse>>{

        return object : NetworkBoundResource<HotelsResponse>() {
            override fun createCall(): LiveData<Resource<HotelsResponse>> {

                return apiService.getHotelsFromLocationSearch(query, currency)
            }

        }.asLiveData()
    }
}