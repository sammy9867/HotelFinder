package com.thesis.hotelfinder.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.thesis.hotelfinder.R
import com.thesis.hotelfinder.databinding.FragmentHotelsBinding


class HotelsFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // binding object that holds all views in the given fragment
        val binding: FragmentHotelsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_hotels, container, false
        )

        return binding.root
    }


}
