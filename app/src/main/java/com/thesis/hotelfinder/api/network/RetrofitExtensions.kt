package com.thesis.hotelfinder.api.network

import retrofit2.Response
import retrofit2.Retrofit

/**
 * Synthetic sugaring to create Retrofit Service.
 */
inline fun <reified T> Retrofit.create(): T = create(T::class.java)

/**
 * Converts Retrofit [Response] to [Resource] which provides state
 * and data to the UI.
 */
fun <TypeResult> Response<TypeResult>.toResource(): Resource<TypeResult> {
    val error = errorBody()?.string()
    return when {
        isSuccessful -> {
            val body = body()
            when {
                body != null -> Resource.success(body)
                else -> Resource.error(error)
            }
        }
        else -> Resource.error(error)
    }
}