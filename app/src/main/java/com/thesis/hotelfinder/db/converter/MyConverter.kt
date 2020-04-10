package com.thesis.hotelfinder.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thesis.hotelfinder.model.*

class MyConverter{

    companion object{

        @TypeConverter
        @JvmStatic
        fun photofromString(value: String): Photo? {
            val photo =  object: TypeToken<Photo?>(){}.type
            return  Gson().fromJson(value, photo)
        }


        @TypeConverter
        @JvmStatic
        fun stringfromPhoto(photo: Photo?): String {
            val gson =  Gson()
            return gson.toJson(photo)
        }



        @TypeConverter
        @JvmStatic
        fun imagesfromString(value: String): Images? {
            val images =  object: TypeToken<Images?>(){}.type
            return  Gson().fromJson(value, images)
        }

        @TypeConverter
        @JvmStatic
        fun stringfromImages(images: Images?): String {
            val gson =  Gson()
            return gson.toJson(images)
        }


        @TypeConverter
        @JvmStatic
        fun originalfromString(value: String): Original? {
            val original =  object: TypeToken<Original?>(){}.type
            return  Gson().fromJson(value, original)
        }


        @TypeConverter
        @JvmStatic
        fun stringfromOriginal(original: Original?): String {
            val gson =  Gson()
            return gson.toJson(original)
        }

        @TypeConverter
        @JvmStatic
        fun awardsfromString(value: String): List<Award>? {
            val awards =  object: TypeToken< List<Award>?>(){}.type
            return  Gson().fromJson(value, awards)
        }


        @TypeConverter
        @JvmStatic
        fun stringfromAwards(awards:  List<Award>?): String {
            val gson =  Gson()
            return gson.toJson(awards)
        }


        @TypeConverter
        @JvmStatic
        fun amenitiesfromString(value: String): List<Amenity>? {
            val amenities =  object: TypeToken< List<Amenity>?>(){}.type
            return  Gson().fromJson(value, amenities)
        }


        @TypeConverter
        @JvmStatic
        fun stringfromAmenities(amenities:  List<Amenity>?): String {
            val gson =  Gson()
            return gson.toJson(amenities)
        }
    }

}