package com.thesis.hotelfinder.api

import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.UnSplashPhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UnSplashApiServices {

    @GET("search/photos")
    fun getPhotos(
        @Query("query") query: String
    ): LiveData<Resource<UnSplashPhotosResponse>>

}
