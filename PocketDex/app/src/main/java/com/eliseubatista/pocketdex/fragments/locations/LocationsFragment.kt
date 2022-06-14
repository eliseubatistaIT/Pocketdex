package com.eliseubatista.pocketdex.fragments.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.FragmentLocationsBinding


class LocationsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentLocationsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_locations, container, false)

        return binding.root
    }

}