package com.thesis.hotelfinder.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.thesis.hotelfinder.api.network.ApiResponse
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator.tripAdvisorApiService
import com.thesis.hotelfinder.api.response.LocationSearchResponse
import com.thesis.hotelfinder.db.AppDatabase
import com.thesis.hotelfinder.db.dao.LocationSearchDao
import com.thesis.hotelfinder.model.LocationSearch
import com.thesis.hotelfinder.util.AppExecutors
import kotlinx.coroutines.*

class LocationSearchRepository(context: Context,
                               private var locationSearchDao: LocationSearchDao =
                                   AppDatabase.getDatabase(context)!!.locationSearchDao()){

    companion object {
        private var instance : LocationSearchRepository? = null
        fun getInstance(context : Context) : LocationSearchRepository {
            if (instance == null) {
                instance = LocationSearchRepository(context)
            }

            return instance as LocationSearchRepository
        }
    }

    fun getLocationIdFromLocationSearch
                (query: String, currency: String): LiveData<Resource<LocationSearch>> {

        //<ResultType, RequestType>
        return object : NetworkBoundResource<LocationSearch, LocationSearchResponse>(AppExecutors.getInstance()) {

            override fun saveCallResult(item: LocationSearchResponse) {
                CoroutineScope(Dispatchers.IO).launch {

                    for(i in item.data){
                        if(i.result_type == "geos"){
                            val resultObj = i.result_object
                            val locationSearch = LocationSearch(resultObj!!.location_id, query, resultObj.latitude, resultObj.longitude)
                            locationSearchDao.insertLocation(locationSearch)
                            break
                        }
                    }
                }

            }

            override fun shouldFetch(data: LocationSearch?) = data == null

            override fun loadFromDb(): LiveData<LocationSearch> {
               return  locationSearchDao.getLocationByName(query)
            }

            override fun createCall(): LiveData<ApiResponse<LocationSearchResponse>> {
                return tripAdvisorApiService.getLocationIdFromLocationSearch(query, currency)
            }
        }.asLiveData()
    }
}