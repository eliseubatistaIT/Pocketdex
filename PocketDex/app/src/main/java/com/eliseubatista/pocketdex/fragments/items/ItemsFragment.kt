package com.eliseubatista.pocketdex.fragments.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.FragmentItemsBinding

class ItemsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_items, container, false)

        return binding.root
    }
}