package com.thesis.hotelfinder.api

import androidx.lifecycle.LiveData
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.response.HotelDetailsResponse
import com.thesis.hotelfinder.api.response.HotelResponse
import com.thesis.hotelfinder.api.response.LocationSearchResponse
import com.thesis.hotelfinder.util.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TripAdvisorApiServices{

    @GET("locations/search?sort=relevance")
    fun getLocationIdFromLocationSearch(
        @Query("query") query: String,
        @Query("currency") currency: String
    ) : LiveData<Resource<LocationSearchResponse>>


    @GET("hotels/list")
    fun getHotelsListFromLocationId(
        @Query("location_id") location_id: Int,
        @Query("checkin") check_in_date: String,
        @Query("adults") number_of_adults: Int,
        @Query("rooms") number_of_rooms: Int)
      : LiveData<Resource<HotelResponse>>


    @GET("hotels/get-details")
    fun getHotelDetailsListFromLocationId(
        @Query("location_id") location_id: Int)
            : LiveData<Resource<HotelDetailsResponse>>

}