package com.thesis.hotelfinder.api.response

import com.google.gson.annotations.SerializedName
import com.thesis.hotelfinder.model.LocationSearch

data class LocationSearchResponse(@SerializedName("data") var data : ArrayList<LocationSearchData>)

data class LocationSearchData(@SerializedName("result_type") var result_type: String,
                              @SerializedName("result_object") var result_object: LocationSearch?
)

