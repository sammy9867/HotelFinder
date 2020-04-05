package com.thesis.hotelfinder.api.response

import com.google.gson.annotations.SerializedName

data class UnSplashPhotosResponse(@SerializedName("results") val results : List<UnSplashResults>)

data class UnSplashResults(@SerializedName("id") val id : String,
                           @SerializedName("width") val width : Int,
                           @SerializedName("height") val height : Int,
                           @SerializedName("color") val color : String,
                           @SerializedName("urls") val urls : UnSplashImageUrls)

data class UnSplashImageUrls(@SerializedName("raw") val raw : String,
                             @SerializedName("full") val full : String,
                             @SerializedName("regular") val regular : String,
                             @SerializedName("small") val small : String,
                             @SerializedName("thumb") val thumb : String)