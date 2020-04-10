package com.thesis.hotelfinder.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "location_search_table")
data class LocationSearch(

    @PrimaryKey
    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    var location_id: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String,


    @ColumnInfo(name = "latitude")
    @SerializedName("latitude")
    var latitude: Double,


    @ColumnInfo(name = "longitude")
    @SerializedName("longitude")
    var longitude: Double
): Parcelable