package com.thesis.hotelfinder.api.response

import com.google.gson.annotations.SerializedName
import com.thesis.hotelfinder.model.LocationSearch

data class LocationSearchResponse(@SerializedName("data") val data : List<LocationSearchData>)

data class LocationSearchData(@SerializedName("result_type") val result_type: String,
                              @SerializedName("result_object") val result_object: LocationSearch
)

