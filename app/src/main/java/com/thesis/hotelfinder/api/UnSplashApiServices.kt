package com.thesis.hotelfinder.api

import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.UnSplashPhotosResponse
import com.thesis.hotelfinder.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface UnSplashApiServices {

    @GET("search/photos?client_id=" +Constants.UNSPLASH_ACCESS_KEY)
    fun getPhotos(
        @Query("query") query: String
    ): LiveData<Resource<UnSplashPhotosResponse>>

}
