package com.thesis.hotelfinder.api

import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.HotelsResponse
import com.thesis.hotelfinder.util.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.*

interface ApiServices{

    @Headers(
        "x-rapidapi-host: ${Constants.API_HOST}",
        "x-rapidapi-key: ${Constants.API_KEY}"
    )
    @GET("locations/search?sort=relevance")
    fun getHotelsFromLocationSearch(
        @Query("query") query: String,
        @Query("currency") currency: String
    ) : LiveData<Resource<HotelsResponse>>

}