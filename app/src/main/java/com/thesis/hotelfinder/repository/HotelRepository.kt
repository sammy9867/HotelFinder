package com.thesis.hotelfinder.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.GsonBuilder
import com.thesis.hotelfinder.api.network.ApiResponse
import com.thesis.hotelfinder.api.network.NetworkBoundResource
import com.thesis.hotelfinder.api.network.Resource
import com.thesis.hotelfinder.api.network.ServiceGenerator.tripAdvisorApiService
import com.thesis.hotelfinder.api.response.HotelResponse
import com.thesis.hotelfinder.db.AppDatabase
import com.thesis.hotelfinder.db.dao.HotelDao
import com.thesis.hotelfinder.model.Hotel
import com.thesis.hotelfinder.util.AppExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HotelRepository(context: Context,
                      private var hotelDao: HotelDao =
                          AppDatabase.getDatabase(context)!!.hotelDao()){

    companion object {
        private var instance : HotelRepository? = null
        fun getInstance(context : Context) : HotelRepository {
            if (instance == null) {
                instance = HotelRepository(context)
            }

            return instance as HotelRepository
        }
    }


    fun getHotelsListFromLocationId(location_id: Int, check_in_date: String,
    number_of_adults: Int, number_of_rooms :Int, number_of_nights: Int, max_price: Int, hotel_class: Float,
                                    amenityInput: String): LiveData<Resource<List<Hotel>>> {

        //<ResultType, RequestType>
        return object : NetworkBoundResource<List<Hotel>, HotelResponse>(AppExecutors.getInstance()) {

            override fun saveCallResult(item: HotelResponse) {
                Log.i("REPO", "Inserting value into DB")
               // Log.i("REPO", GsonBuilder().setPrettyPrinting().create().toJson(item.data))
                CoroutineScope(Dispatchers.IO).launch {
                    val hotelList: ArrayList<Hotel> = arrayListOf()
                       // Log.i("REPO INS", "isSucc")// GsonBuilder().setPrettyPrinting().create().toJson(item.data))//GsonBuilder().setPrettyPrinting().create().toJson(item.data))
                    for(i in item.data){
                        val hotel = Hotel(i.location_id, location_id, check_in_date,
                            number_of_adults, number_of_rooms, number_of_nights,
                            max_price, hotel_class, amenityInput,
                            i.name, i.latitude, i.longitude, i.num_reviews, i.ranking, i.rating,
                            i.price_level, i.price, i.photo
                        )
                        hotelList.add(hotel)
                    }
                    hotelDao.insertHotels(hotelList)


                }
            }

            override fun shouldFetch(data: List<Hotel>?) = data == null

            override fun loadFromDb(): LiveData<List<Hotel>> {
                Log.i("REPO", "Loading from DB")
                val hotelListLiveData : MutableLiveData<List<Hotel>> = MutableLiveData()
                var hotelList: List<Hotel>?
                CoroutineScope(Dispatchers.IO).launch{
                    hotelList = hotelDao.getAllHotelsByLocationId(location_id, check_in_date,
                        number_of_adults, number_of_rooms, number_of_nights,
                        max_price, hotel_class, amenityInput)
                    if(!hotelList.isNullOrEmpty()){
                        Log.i("REPO SIZE", "" + hotelList!!.size)
                       // Log.i("REPO", "" + hotelList!![0].location_id + " " + hotelList!![0].name)
                        hotelListLiveData.postValue(hotelList)
                    }else{
                        Log.i("REPO", "hotelList is null")
                        hotelListLiveData.postValue(null)
                    }

                }
                return hotelListLiveData
            }

            override fun createCall(): LiveData<ApiResponse<HotelResponse>> {
                Log.i("REPO", "CALLING API")
                return tripAdvisorApiService.getHotelsListFromLocationId(location_id, check_in_date, number_of_adults, number_of_rooms,
                    number_of_nights, max_price, hotel_class, amenityInput)
            }
        }.asLiveData()
    }
}