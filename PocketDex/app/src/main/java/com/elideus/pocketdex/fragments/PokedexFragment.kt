package com.elideus.pocketdex.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.elideus.pocketdex.R
import com.elideus.pocketdex.databinding.FragmentPokedexBinding

class PokedexFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokedexBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex, container, false)

        return binding.root
    }

}