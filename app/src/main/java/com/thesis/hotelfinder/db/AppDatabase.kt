package com.thesis.hotelfinder.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.thesis.hotelfinder.db.converter.MyConverter
import com.thesis.hotelfinder.db.dao.HotelDao
import com.thesis.hotelfinder.db.dao.HotelDetailsDao
import com.thesis.hotelfinder.db.dao.LocationSearchDao
import com.thesis.hotelfinder.model.*

@Database(entities = [LocationSearch::class, Hotel::class, Photo::class,  Images::class,
Original::class, HotelDetails::class, Amenity::class], version = 1, exportSchema = false)
@TypeConverters(MyConverter::class)
abstract class AppDatabase: RoomDatabase(){

    abstract fun locationSearchDao() : LocationSearchDao
    abstract fun hotelDao() : HotelDao
    abstract fun hotelDetailsDao() : HotelDetailsDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {

                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, "app_database"
                        )
                            .build()

                }
            }
            return INSTANCE
        }
    }
}