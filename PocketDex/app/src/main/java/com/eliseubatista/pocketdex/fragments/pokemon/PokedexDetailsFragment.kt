package com.eliseubatista.pocketdex.fragments.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.FragmentPokedexDetailsBinding
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.utils.*

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

        val bundle = requireArguments()
        pokemonName = bundle.getString("POKEMON_NAME", "")

        viewModelFactory = PokemonDetailsViewModel.Factory(requireActivity().application, pokemonName)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PokemonDetailsViewModel::class.java)

        /*
        (activity as AppCompatActivity).supportActionBar?.title =
            "${formatPocketdexObjectName(pokemonName)} details" */

        viewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer { pokemon -> refreshPokemonDisplay(binding, pokemon) })

        return binding.root
    }

    private fun refreshPokemonDisplay(
        binding: FragmentPokedexDetailsBinding,
        pokemon: PokemonModel
    ) {
        refreshPokemonHeader(binding, pokemon)
        refreshPokemonAbout(binding, pokemon)
        refreshPokemonStats(binding, pokemon)
    }

    private fun refreshPokemonHeader(
        binding: FragmentPokedexDetailsBinding,
        pokemon: PokemonModel
    ) {
        loadImageWithGlide(pokemon.maleSprite, binding.pokemonDetailsAvatar)

        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)

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
            val typeOneColor = getPokemonTypeColor(requireContext(), pokemon.types[0])

            binding.pokemonDetailsFirstTypeText.text = pokemon.types[0]
            binding.pokemonDetailsFirstTypeText.setTextColor(
                getTextColorByBackgroundColor(
                    requireContext(),
                    typeOneColor
                )
            )

            binding.pokemonDetailsFirstTypeBackground.setColorFilter(typeOneColor)

            if (pokemon.types.size > 1) {
                val typeTwoColor = getPokemonTypeColor(requireContext(), pokemon.types[1])

                binding.pokemonDetailsSecondTypeText.text = pokemon.types[1]
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

    private fun refreshPokemonAbout(binding: FragmentPokedexDetailsBinding, pokemon: PokemonModel) {
        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)

        binding.pokemonDetailsDescriptionText.text =
            formatPocketdexObjectDescription(pokemon.flavor)

        binding.pokemonDetailsBaseStatsFixedText.setTextColor(pokemonColor)

        binding.pokemonDetailsBar1.setColorFilter(pokemonColor)
        binding.pokemonDetailsBar2.setColorFilter(pokemonColor)

        binding.pokemonDetailsSpeciesText.text = formatPokemonGenus(pokemon.genus)

        binding.pokemonDetailsHeightText.text = formatPokemonHeight(pokemon.height)

        binding.pokemonDetailsWeightText.text = formatPokemonWeight(pokemon.weight)
    }

    private fun refreshPokemonStats(binding: FragmentPokedexDetailsBinding, pokemon: PokemonModel) {
        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)

        binding.pokemonDetailsHpText.text = pokemon.hp.toString()
        binding.pokemonDetailsAttackText.text = pokemon.attack.toString()
        binding.pokemonDetailsDefenseText.text = pokemon.defense.toString()
        binding.pokemonDetailsSpAttackText.text = pokemon.specialAttack.toString()
        binding.pokemonDetailsSpDefenseText.text = pokemon.specialDefense.toString()
        binding.pokemonDetailsSpeedText.text = pokemon.speed.toString()
    }
}