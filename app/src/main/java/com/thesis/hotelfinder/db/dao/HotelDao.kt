package com.thesis.hotelfinder.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.thesis.hotelfinder.model.Hotel

@Dao
interface HotelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHotels(hotels: List<Hotel>)

    @Transaction
    @Query("""select * from hotel_table where location_search_id = :locationSearchId
                    and max_price = :maxPrice and hotel_class = :hotelClass""" )
    fun getAllHotelsByLocationId(locationSearchId: Int, maxPrice: Int, hotelClass: Float) : List<Hotel>
}
