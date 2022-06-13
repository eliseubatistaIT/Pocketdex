package com.eliseubatista.pocketdex.fragments.pokemon

import PokedexDetailsAboutFragment
import PokedexDetailsEvolutionsFragment
import PokedexDetailsStatsFragment
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.FragmentPokedexDetailsBinding
import com.eliseubatista.pocketdex.fragments.locations.LocationsFragment
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.utils.*
import com.eliseubatista.pocketdex.views.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class PokedexDetailsFragment : Fragment() {
    private var pokemonName = ""
    private lateinit var viewModel: PokemonDetailsViewModel
    private lateinit var viewModelFactory: PokemonDetailsViewModel.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokedexDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex_details, container, false)

        setUpViewPagerAndTabLayout(binding)

        val bundle = requireArguments()
        pokemonName = bundle.getString("POKEMON_NAME", "")

        viewModelFactory =
            PokemonDetailsViewModel.Factory(requireActivity().application, pokemonName)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PokemonDetailsViewModel::class.java)

        viewModel.isInFavorites.observe(
            viewLifecycleOwner,
            Observer { inFavorites ->
                refreshFavorites(binding, inFavorites)
            })


        viewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer { pokemon -> refreshPokemonHeader(binding, pokemon) })

        binding.toolbarPokedexDetails.favorite.setOnClickListener { view: View ->
            viewModel.addOrRemoveFavorite()
        }
        binding.toolbarPokedexDetails.arrowBack.setOnClickListener { view: View -> parentFragmentManager.popBackStack() }

        return binding.root
    }

    private fun setUpViewPagerAndTabLayout(binding: FragmentPokedexDetailsBinding) {
        val viewPager = binding.pokedexDetailsViewPager
        val tabLayout = binding.pokedexDetailsTabLayout

        val adapter = SectionPagerAdapter(childFragmentManager, lifecycle)
        adapter.addFragment(PokedexDetailsAboutFragment(), "About")
        adapter.addFragment(PokedexDetailsStatsFragment(), "Stats")
        adapter.addFragment(PokedexDetailsEvolutionsFragment(), "Evolutions")

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }


        })

    }

    private fun refreshPokemonHeader(
        binding: FragmentPokedexDetailsBinding,
        pokemon: PokemonModel
    ) {

        val imageScale = getImageScaleByEvolutionChain(pokemon.name, pokemon.evolutionChain)

        binding.pokemonDetailsAvatar.scaleX = imageScale
        binding.pokemonDetailsAvatar.scaleY = imageScale

        loadImageWithGlide(pokemon.maleSprite, binding.pokemonDetailsAvatar)

        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)
        val textColor = getTextColorByBackgroundColor(requireContext(), pokemonColor)

        binding.pokemonDetailsBackground.setColorFilter(pokemonColor)

        binding.toolbarPokedexDetails.pokemonName.text =
            formatPocketdexObjectName(pokemon.name)

        binding.toolbarPokedexDetails.pokemonId.text = "#${pokemon.id}"

        binding.toolbarPokedexDetails.pokemonName.setTextColor(
            getTextColorByBackgroundColor(
                requireContext(),
                pokemonColor
            )
        )

        binding.toolbarPokedexDetails.pokemonId.setTextColor(
            getTextColorByBackgroundColor(
                requireContext(),
                pokemonColor
            )
        )

        binding.toolbarPokedexDetails.arrowBack.setColorFilter(textColor)
        binding.toolbarPokedexDetails.favorite.setColorFilter(textColor)
    }

    private fun refreshFavorites(binding: FragmentPokedexDetailsBinding, isInFavorites: Boolean) {
        if (isInFavorites) {
            binding.toolbarPokedexDetails.favorite.setImageResource(R.drawable.ic_star_fill)
        } else {
            binding.toolbarPokedexDetails.favorite.setImageResource(R.drawable.ic_star_border)
        }
    }

}