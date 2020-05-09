package com.thesis.hotelfinder.api.response

import com.google.gson.annotations.SerializedName
import com.thesis.hotelfinder.model.Hotel

data class HotelResponse(@SerializedName("data") var data : List<Hotel>)