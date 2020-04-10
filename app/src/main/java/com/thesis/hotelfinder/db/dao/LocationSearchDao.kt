package com.thesis.hotelfinder.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thesis.hotelfinder.model.LocationSearch


@Dao
interface LocationSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setLocation(locationSearch: LocationSearch)

    @Query("select * from location_search_table where name=:name")
    fun getLocationByName(name: String) : LocationSearch?

    @Query("select * from location_search_table where location_id=:locationId")
    fun getLocationById(locationId: Int) : LocationSearch?

    @Query("select * from location_search_table")
    fun getAllLocations() : List<LocationSearch>
}