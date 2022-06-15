package com.eliseubatista.pocketdex.fragments.regions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.database.regions.DatabaseRegions
import com.eliseubatista.pocketdex.databinding.FragmentRegionLocationsBinding
import com.eliseubatista.pocketdex.utils.*

class RegionLocationsFragment : Fragment() {

    private var regionsName = ""
    private lateinit var viewModel: RegionDetailsViewModel
    private lateinit var viewModelFactory: RegionDetailsViewModel.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentRegionLocationsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_region_locations,
                container,
                true
            )

        viewModelFactory =
            RegionDetailsViewModel.Factory(requireActivity().application, regionsName)

        viewModel =
            ViewModelProvider(
                requireParentFragment(),
                viewModelFactory
            )[RegionDetailsViewModel::class.java]

        viewModel.region.observe(
            viewLifecycleOwner
        ) { region -> refreshRegionsLocations(binding, region) }

        return binding.root
    }


    private fun refreshRegionsLocations(
        binding: FragmentRegionLocationsBinding,
        region: DatabaseRegions
    ) {
        binding.regionsDetailsDescriptionText.text =
            formatPocketdexObjectDescription(region.name)
    }
}