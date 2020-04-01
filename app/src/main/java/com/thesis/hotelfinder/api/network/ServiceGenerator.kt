package com.thesis.hotelfinder.api.network

import com.thesis.hotelfinder.api.ApiServices
import com.thesis.hotelfinder.util.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator{

    //creating a Network Interceptor to add api_host and api_key in all the request as Interceptor
    private val interceptor = Interceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .header("x-rapidapi-host",Constants.API_HOST)
            .header("x-rapidapi-key", Constants.API_KEY)
            .build()
        chain.proceed(request)
    }

    // we are creating a networking client using OkHttp and add our Interceptor.
    private val apiClient = OkHttpClient().newBuilder().addInterceptor(interceptor).build()


    private fun getRetrofit(URL : String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .client(apiClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }
    val apiService: ApiServices =
        getRetrofit("https://tripadvisor1.p.rapidapi.com/").create(ApiServices::class.java)

}

