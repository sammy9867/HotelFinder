package com.thesis.hotelfinder.model

import com.google.gson.annotations.SerializedName

data class LocationSearch(@SerializedName("location_id") val location_id: Int,
                          @SerializedName("name") val name: String,
                          @SerializedName("latitude") val latitude: Double,
                          @SerializedName("longitude") val longitude: Double)