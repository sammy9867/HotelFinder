package com.thesis.hotelfinder.adapter

import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.textview.MaterialTextView


interface OnHotelListener {
    fun onHotelClick(position: Int, iv: AppCompatImageView, tv: MaterialTextView)
}