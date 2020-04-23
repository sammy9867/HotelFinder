package com.thesis.hotelfinder.model

import android.os.Parcelable
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName
import com.thesis.hotelfinder.db.converter.MyConverter
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "hotel_table",
        foreignKeys = [ForeignKey(entity = LocationSearch::class,
                                  parentColumns = arrayOf("location_id"),
                                  childColumns = arrayOf("location_search_id"),
                                  onDelete = CASCADE)])
data class Hotel(
    @PrimaryKey
    @ColumnInfo(name = "location_id")
    @SerializedName("location_id")
    var location_id: Int,

    @ColumnInfo(name = "location_search_id", index = true)
    var location_search_id: Int,

    @ColumnInfo(name = "max_price")
    var max_price: Int,

    @ColumnInfo(name = "hotel_class")
    var hotel_class: Float,

    @ColumnInfo(name = "name")
    @SerializedName("name") var name: String,

    @ColumnInfo(name = "latitude")
    @SerializedName("latitude") var latitude: Double,

    @ColumnInfo(name = "longitude")
    @SerializedName("longitude") var longitude: Double,

    @ColumnInfo(name = "num_reviews")
    @SerializedName("num_reviews") var num_reviews: Int?,

    @ColumnInfo(name = "ranking")
    @SerializedName("ranking") var ranking: String?,

    @ColumnInfo(name = "rating")
    @SerializedName("rating") var rating: Float?,

    @ColumnInfo(name = "price_level")
    @SerializedName("price_level") var price_level: String?,

    @ColumnInfo(name = "price")
    @SerializedName("price") var price: String?,

    @ColumnInfo(name = "photo")
    @TypeConverters(MyConverter::class)
    @SerializedName("photo")
    var photo: Photo?) : Parcelable

@Parcelize
@Entity(tableName = "hotel_details_table")
data class HotelDetails(

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
    var longitude: Double,

    @ColumnInfo(name = "num_reviews")
    @SerializedName("num_reviews")
    var num_reviews: Int?,

    @ColumnInfo(name = "ranking")
    @SerializedName("ranking")
    var ranking: String?,

    @ColumnInfo(name = "rating")
    @SerializedName("rating")
    var rating: Float?,

    @ColumnInfo(name = "hotel_class")
    @SerializedName("hotel_class")
    var hotel_class: String,

    @ColumnInfo(name = "phone")
    @SerializedName("phone")
    var phone: String?,

    @ColumnInfo(name = "website")
    @SerializedName("website")
    var website: String,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    var email: String?,

    @ColumnInfo(name = "address")
    @SerializedName("address")
    var address: String?,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String?,

    @ColumnInfo(name = "price_level")
    @SerializedName("price_level")
    var price_level: String?,

    @ColumnInfo(name = "price")
    @SerializedName("price")
    var price: String?,

    @ColumnInfo(name = "photo")
    @SerializedName("photo")
    @TypeConverters(MyConverter::class)
    var photo: Photo?,

    @ColumnInfo(name = "amenities")
    @SerializedName("amenities")
    @TypeConverters(MyConverter::class)
    var amenities: List<Amenity>): Parcelable



@Parcelize
@Entity(tableName = "photo_table")
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = "images")
    @SerializedName("images")
    @TypeConverters(MyConverter::class)
    var images: Images) : Parcelable

@Parcelize
@Entity(tableName = "images_table")
data class Images(
    @PrimaryKey
    @ColumnInfo(name = "original")
    @SerializedName("original")
    @TypeConverters(MyConverter::class)
    var original: Original): Parcelable

@Parcelize
@Entity(tableName = "original_table")
data class Original(
    @PrimaryKey
    @ColumnInfo(name = "url")
    @SerializedName("url")
    var url: String,

    @ColumnInfo(name = "width")
    @SerializedName("width")
    var width: String,

    @ColumnInfo(name = "height")
    @SerializedName("height")
    var height: String): Parcelable


@Parcelize
@Entity(tableName = "amenities_table")
data class Amenity(
    @PrimaryKey
    @ColumnInfo(name = "key")
    @SerializedName("key")
    var key: String,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String): Parcelable