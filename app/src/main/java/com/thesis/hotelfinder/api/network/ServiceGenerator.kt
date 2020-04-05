package com.thesis.hotelfinder.api.network

import com.thesis.hotelfinder.api.TripAdvisorApiServices
import com.thesis.hotelfinder.api.UnSplashApiServices
import com.thesis.hotelfinder.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator{

    // Creating a Network Interceptor to add api_host and api_key in all the request as Interceptor
    private val interceptorTripAdvisor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .header("x-rapidapi-host",Constants.RAPID_API_HOST)
            .header("x-rapidapi-key", Constants.RAPID_API_KEY)
            .build()
        chain.proceed(request)
    }

    private val interceptorUnSplash = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .header("client_id",Constants.UNSPLASH_ACCESS_KEY)
            .build()
        chain.proceed(request)
    }

    // Creating a networking client using OkHttp and add our Interceptor.
    private val apiClientTripAdvisor = OkHttpClient().newBuilder().addInterceptor(interceptorTripAdvisor).build()
    private val apiClientUnSplash= OkHttpClient().newBuilder().addInterceptor(interceptorUnSplash).build()

    private fun getRetrofitForTripAdvisor(URL : String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .client(apiClientTripAdvisor)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    private fun getRetrofitForUnSplash(URL : String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .client(apiClientTripAdvisor)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }


    val tripAdvisorApiService: TripAdvisorApiServices =
        getRetrofitForTripAdvisor("https://tripadvisor1.p.rapidapi.com/").create(TripAdvisorApiServices::class.java)

    val unSplashApiService: UnSplashApiServices =
        getRetrofitForUnSplash("https://api.unsplash.com/").create(UnSplashApiServices::class.java)

}

