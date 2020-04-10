package com.thesis.hotelfinder.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
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
                Log.i("REPO", "Inserting value into DB")
                Log.i("isSucc", GsonBuilder().setPrettyPrinting().create().toJson(item.data))
                CoroutineScope(Dispatchers.IO).launch {

                    for(i in item.data){
                        if(i.result_type == "geos"){
                            val resultObj = i.result_object
                            val locationSearch = LocationSearch(resultObj!!.location_id, query, resultObj.latitude, resultObj.longitude)
                            locationSearchDao.setLocation(locationSearch)
                            break
                        }
                    }
                }

            }

            override fun shouldFetch(data: LocationSearch?) = data == null

            override fun loadFromDb(): LiveData<LocationSearch> {
               Log.i("REPO", "Loading from DB")
                val locationLiveData: MutableLiveData<LocationSearch> = MutableLiveData()
                CoroutineScope(Dispatchers.IO).launch{
                    val locationSearch =  locationSearchDao.getLocationByName(query)
                    if(locationSearch != null){
                        Log.i("REPO", "" + locationSearch.location_id + " " + locationSearch.name)
                        locationLiveData.postValue(locationSearch)
                    }else{
                        Log.i("REPO", "locationSearch is null")
                        locationLiveData.postValue(null)
                    }
                }

                return locationLiveData
            }

            override fun createCall(): LiveData<Resource<LocationSearchResponse>> {
                Log.i("REPO", "CALLING API")
                return tripAdvisorApiService.getLocationIdFromLocationSearch(query, currency)
            }
        }.asLiveData()
    }
}