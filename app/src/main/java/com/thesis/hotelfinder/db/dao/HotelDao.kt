package com.thesis.hotelfinder.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thesis.hotelfinder.model.Hotel

@Dao
interface HotelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHotels(hotels: List<Hotel>)

    @Transaction
    @Query(
        """SELECT * FROM hotel_table
        WHERE location_search_id = :locationSearchId AND checkIn = :checkIn
        AND adults = :adults AND rooms = :rooms AND nights = :nights AND
        max_price = :maxPrice AND hotel_class = :hotelClass AND amenityInput = :amenityInput"""
    )
    fun getAllHotelsByLocationId(locationSearchId: Int, checkIn: String,
                                 adults: Int,  rooms: Int,  nights: Int,
                                 maxPrice: Int, hotelClass: Float,
                                 amenityInput: String) : List<Hotel>
}
