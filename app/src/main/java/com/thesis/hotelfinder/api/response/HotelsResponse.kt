package com.thesis.hotelfinder.api.response

import com.google.gson.annotations.SerializedName
import com.thesis.hotelfinder.model.Hotels

data class HotelsResponse(@SerializedName("data") val data : List<Hotels>)