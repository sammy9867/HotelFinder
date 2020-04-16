package com.thesis.hotelfinder.api.response

import com.google.gson.annotations.SerializedName
import com.thesis.hotelfinder.model.HotelDetails

data class HotelDetailsResponse(@SerializedName("data") var data : List<HotelDetails>)