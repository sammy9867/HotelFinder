package com.thesis.hotelfinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelFilter(var max_price: Int, var hotel_class: Float): Parcelable