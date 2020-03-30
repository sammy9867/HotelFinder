package com.thesis.hotelfinder.api.response

import com.google.gson.annotations.SerializedName

data class HotelsResponse(@SerializedName("data") val data : List<Data>)

data class Data(@SerializedName("result_type") val result_type: String,
                @SerializedName("result_object") val result_object: ResultObject)

data class ResultObject(@SerializedName("location_id") val location: String,
                        @SerializedName("name") val name: String)