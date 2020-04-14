package com.thesis.hotelfinder.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LocationSearchViewModel::class.java)) {
            LocationSearchViewModel(context) as T
        }else if (modelClass.isAssignableFrom(HotelsViewModel::class.java)) {
            HotelsViewModel(context) as T
        }else if (modelClass.isAssignableFrom(HotelDetailsViewModel::class.java)) {
            HotelDetailsViewModel(context) as T
        }else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}