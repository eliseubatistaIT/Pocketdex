package com.eliseubatista.pocketdex.fragments.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.database.DatabasePokemon
import com.eliseubatista.pocketdex.databinding.FragmentPokedexDetailsEvolutionsBinding
import com.eliseubatista.pocketdex.utils.dpToPx
import com.eliseubatista.pocketdex.utils.getPokemonBackgroundColor
import com.eliseubatista.pocketdex.views.pokemons.PokemonEvolutionChainAdapter

class PokedexDetailsEvolutionsFragment : Fragment() {

    private var pokemonName = ""
    private lateinit var viewModel: PokemonDetailsViewModel
    private lateinit var viewModelFactory: PokemonDetailsViewModel.Factory

    private lateinit var evolutionChainAdapter: PokemonEvolutionChainAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokedexDetailsEvolutionsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_pokedex_details_evolutions,
                container,
                true
            )

        viewModelFactory =
            PokemonDetailsViewModel.Factory(requireActivity().application, pokemonName)
        viewModel =
            ViewModelProvider(
                requireParentFragment(),
                viewModelFactory
            )[PokemonDetailsViewModel::class.java]

        setupRecyclerViews(binding)

        viewModel.pokemon.observe(
            viewLifecycleOwner
        ) { pokemon -> refreshPokemonEvolutions(binding, pokemon) }

        return binding.root
    }

    private fun setupRecyclerViews(binding: FragmentPokedexDetailsEvolutionsBinding) {

        evolutionChainAdapter = PokemonEvolutionChainAdapter()
        binding.pokemonDetailsEvolutions.gridView.layoutManager = LinearLayoutManager(context)
        binding.pokemonDetailsEvolutions.gridView.adapter = evolutionChainAdapter
    }

    private fun refreshPokemonEvolutions(
        binding: FragmentPokedexDetailsEvolutionsBinding,
        pokemon: DatabasePokemon
    ) {

        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)
        binding.pokemonDetailsEvolutions.evolutionFixedText.setTextColor(pokemonColor)

        val size = dpToPx(requireContext(), viewModel.pokeEvolutionChain.size * 120)

        binding.pokemonDetailsEvolutions.gridView.layoutParams.height = size

        if (viewModel.pokeEvolutionChain.size < 1) {
            binding.pokemonDetailsEvolutions.evolutionFixedText.visibility = View.GONE
        } else {
            binding.pokemonDetailsEvolutions.evolutionFixedText.visibility = View.VISIBLE
        }

        evolutionChainAdapter.submitList(viewModel.pokeEvolutionChain)
    }
}