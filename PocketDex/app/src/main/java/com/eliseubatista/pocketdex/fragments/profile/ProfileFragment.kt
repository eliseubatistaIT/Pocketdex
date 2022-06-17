package com.eliseubatista.pocketdex.fragments.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.database.pokemons.DatabasePokemon
import com.eliseubatista.pocketdex.databinding.FragmentPokedexDetailsBinding
import com.eliseubatista.pocketdex.databinding.FragmentProfileBinding
import com.eliseubatista.pocketdex.fragments.pokemon.PokedexDetailsAboutFragment
import com.eliseubatista.pocketdex.fragments.pokemon.PokedexDetailsEvolutionsFragment
import com.eliseubatista.pocketdex.fragments.pokemon.PokedexDetailsStatsFragment
import com.eliseubatista.pocketdex.fragments.pokemon.PokemonDetailsViewModel
import com.eliseubatista.pocketdex.utils.*
import com.eliseubatista.pocketdex.views.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private var pokemonName = ""
    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewModelFactory: ProfileViewModel.Factory

    private lateinit var progressBarDialog: ProgressBarDialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        setUpViewPagerAndTabLayout(binding)

        viewModelFactory =
            ProfileViewModel.Factory(requireActivity().application)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]

        progressBarDialog = ProgressBarDialog(inflater, requireContext())

        viewModel.isLoadingProfile.observe(viewLifecycleOwner) {
            Log.i("FDS", "FDS: $it")

            if (it) {
                progressBarDialog.startLoading("Loading Profile..")
            } else {
                progressBarDialog.dismiss()
            }
        }

        return binding.root
    }

    private fun setUpViewPagerAndTabLayout(binding: FragmentProfileBinding) {
        val viewPager = binding.profileViewPager
        val tabLayout = binding.profileTabLayout

        val adapter = SectionPagerAdapter(childFragmentManager, lifecycle)
        adapter.addFragment(ProfilePokemonsFragment(), "Pokemons")
        adapter.addFragment(ProfileItemsFragment(), "Items")
        adapter.addFragment(ProfileRegionsFragment(), "Regions")

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })

    }
}