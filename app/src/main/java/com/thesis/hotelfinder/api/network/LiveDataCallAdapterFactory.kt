package com.thesis.hotelfinder.api.network

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {

        // Check #1
        // Make sure the CallAdapter is returning a type of LiveData
        if (getRawType(returnType) != LiveData::class.java) return null

        // Check #2
        // Type that LiveData is wrapping
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != Resource::class.java) {
            throw IllegalArgumentException("type must be a resource")
        }

        // Check #3
        // Check if ApiResponse is parameterized. AKA: Does ApiResponse<T> exist? (must wrap around T)
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException("resource must be parameterized")
        }

        // get the Response type
        val bodyType =      getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}