package com.thesis.hotelfinder.model

import com.google.gson.annotations.SerializedName

data class HotelDetails(@SerializedName("location_id") val location_id: Int,
                  @SerializedName("name") val name: String,
                  @SerializedName("latitude") val latitude: Double,
                  @SerializedName("longitude") val longitude: Double,
                  @SerializedName("num_reviews") val num_reviews: Int,
                  @SerializedName("ranking") val ranking: String,
                  @SerializedName("rating") val rating: Float,
                  @SerializedName("phone") val phone: String,
                  @SerializedName("website") val website: String,
                  @SerializedName("email") val email: String,
                  @SerializedName("address") val address: String)
