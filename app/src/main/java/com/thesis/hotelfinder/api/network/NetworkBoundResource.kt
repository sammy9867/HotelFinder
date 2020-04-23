package com.thesis.hotelfinder.api.network

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.thesis.hotelfinder.util.AppExecutors
import java.lang.reflect.Type


/**
 *  A generic class that can provide a resource backed by both the room database and the network.
 */
abstract class NetworkBoundResource<ResultType, RequestType>@MainThread constructor(private val appExecutors: AppExecutors){

    private val result = MediatorLiveData<Resource<ResultType>>()

    init{
        result.value = Resource.loading(null)
        val dbSource = this.loadFromDb()

        result.addSource(dbSource) { typeResult->

            result.removeSource(dbSource) // remove source once data is loaded

            if(shouldFetch(typeResult)){
                fetchFromNetwork(dbSource)
            }else{
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData)) }
            }
        }
    }



    private fun fetchFromNetwork(dbSource: LiveData<ResultType>){
        val apiResponse = createCall()
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource(dbSource){ newData->
           setValue(Resource.loading(newData))
        }

        result.addSource(apiResponse){response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)

            when (response){
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(processResponse(response))

                        appExecutors.mainThread().execute {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }

                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        // reload from disk whatever we had
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }

                is ApiErrorResponse -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue)
            result.value = newValue
    }

    protected open fun onFetchFailed(){}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    private fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    // To save the result of the API response into the database.
    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    // Called to get the cached data from the database.
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    // Called to create the API call.
    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

}