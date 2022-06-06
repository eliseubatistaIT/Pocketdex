package com.elideus.pocketdex.fragments.pokedex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elideus.pocketdex.R
import com.elideus.pocketdex.databinding.FragmentPokemonDetailsBinding
import com.elideus.pocketdex.models.pokemon.PokemonModel
import com.elideus.pocketdex.utils.*

class PokemonDetailsFragment : Fragment() {

    private lateinit var pokemonName: String
    private lateinit var viewModel: PokemonDetailsViewModel
    private lateinit var viewModelFactory: PokemonDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokemonDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_details, container, false)

        val args = PokemonDetailsFragmentArgs.fromBundle(requireArguments())
        pokemonName = args.pokemonName

        viewModelFactory = PokemonDetailsViewModelFactory(pokemonName)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PokemonDetailsViewModel::class.java)

        (activity as AppCompatActivity).supportActionBar?.title =
            "${formatPocketdexObjectName(pokemonName)} details"

        viewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer { pokemon -> refreshPokemonDisplay(binding, pokemon) })

        return binding.root
    }

    private fun refreshPokemonDisplay(
        binding: FragmentPokemonDetailsBinding,
        pokemon: PokemonModel
    ) {
        refreshPokemonHeader(binding, pokemon)
        refreshPokemonAbout(binding, pokemon)
    }

    private fun refreshPokemonHeader(
        binding: FragmentPokemonDetailsBinding,
        pokemon: PokemonModel
    ) {
        loadImageWithGlide(pokemon.maleSprite, binding.pokemonDetailsAvatar)

        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.species.color)

        binding.pokemonDetailsBackground.setColorFilter(pokemonColor)

        binding.pokemonDetailsId.text = "#${pokemon.id}"
        binding.pokemonDetailsId.setTextColor(
            getTextColorByBackgroundColor(
                requireContext(),
                pokemonColor
            )
        )

        binding.pokemonDetailsName.text = pokemon.name
        binding.pokemonDetailsName.setTextColor(
            getTextColorByBackgroundColor(
                requireContext(),
                pokemonColor
            )
        )

        if (pokemon.types.size > 0) {
            val typeOneColor = getPokemonTypeColor(requireContext(), pokemon.types[0].name)

            binding.pokemonDetailsFirstTypeText.text = pokemon.types[0].name
            binding.pokemonDetailsFirstTypeText.setTextColor(
                getTextColorByBackgroundColor(
                    requireContext(),
                    typeOneColor
                )
            )

            binding.pokemonDetailsFirstTypeBackground.setColorFilter(typeOneColor)

            if (pokemon.types.size > 1) {
                val typeTwoColor = getPokemonTypeColor(requireContext(), pokemon.types[1].name)

                binding.pokemonDetailsSecondTypeText.text = pokemon.types[1].name
                binding.pokemonDetailsSecondTypeText.setTextColor(
                    getTextColorByBackgroundColor(
                        requireContext(),
                        typeTwoColor
                    )
                )
                binding.pokemonDetailsSecondTypeBackground.setColorFilter(typeTwoColor)
            } else {
                binding.pokemonDetailsSecondType.visibility = View.GONE
            }
        } else {
            binding.pokemonDetailsFirstType.visibility = View.GONE
            binding.pokemonDetailsSecondType.visibility = View.GONE
        }
    }

    private fun refreshPokemonAbout(binding: FragmentPokemonDetailsBinding, pokemon: PokemonModel) {
        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.species.color)

        val flavor = formatPocketdexObjectDescription(pokemon.species.flavor)

        binding.pokemonDetailsDescriptionText.text = flavor

        binding.pokemonDetailsPokedexDataFixedText.setTextColor(pokemonColor)

        binding.pokemonDetaisSpeciesText.text = pokemon.species.genus

        val height = pokemon.height / 10.0f;

        binding.pokemonDetaisHeightText.text = "${height}m"

        val weight = pokemon.weight / 10.0f;

        binding.pokemonDetaisWeightText.text = "${weight}kg"


    }
}