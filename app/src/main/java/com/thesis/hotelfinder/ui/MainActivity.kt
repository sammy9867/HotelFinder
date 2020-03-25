package com.thesis.hotelfinder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thesis.hotelfinder.R
import androidx.databinding.DataBindingUtil
import com.thesis.hotelfinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}
