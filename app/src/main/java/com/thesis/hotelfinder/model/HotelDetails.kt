package com.thesis.hotelfinder.model

import com.google.gson.annotations.SerializedName

data class HotelDetails(@SerializedName("location_id") val location_id: Int,
                        @SerializedName("name") val name: String,
                        @SerializedName("latitude") val latitude: Double,
                        @SerializedName("longitude") val longitude: Double,
                        @SerializedName("num_reviews") val num_reviews: Int,
                        @SerializedName("ranking") val ranking: String,
                        @SerializedName("hotel_class") val hotel_class: String,
                        @SerializedName("rating") val rating: Float,
                        @SerializedName("phone") val phone: String,
                        @SerializedName("website") val website: String,
                        @SerializedName("email") val email: String,
                        @SerializedName("address") val address: String,
                        @SerializedName("price_level") val price_level: String,
                        @SerializedName("price") val price: String,
                        @SerializedName("photo") val photo: Photo,
                        @SerializedName("awards") val awards: List<Award>,
                        @SerializedName("amenities") val amenities: List<Amenity>)



data class Photo(@SerializedName("images") val images: Images)

data class Images(@SerializedName("original") val original: Original)

data class Original(@SerializedName("url") val url: String,
                    @SerializedName("width") val width: String,
                    @SerializedName("height") val height: String)


data class Award(@SerializedName("award_type") val award_type: String,
                 @SerializedName("display_name") val display_name: String,
                 @SerializedName("year") val year: String)

data class Amenity(@SerializedName("key") val key: String,
                   @SerializedName("name") val name: String)