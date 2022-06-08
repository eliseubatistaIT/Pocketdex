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

        viewModelFactory =
            PokemonDetailsViewModel.Factory(requireActivity().application, pokemonName)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PokemonDetailsViewModel::class.java)

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

        val imageScale = getImageScaleByEvolutionChain(pokemon.name, pokemon.evolutionChain)

        binding.pokemonDetailsAvatar.scaleX = imageScale
        binding.pokemonDetailsAvatar.scaleY = imageScale

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

            binding.pokemonDetailsFirstType.typeText.text = pokemon.types[0]
            binding.pokemonDetailsFirstType.typeText.setTextColor(
                getTextColorByBackgroundColor(
                    requireContext(),
                    typeOneColor
                )
            )

            binding.pokemonDetailsFirstType.typeBackground.setColorFilter(typeOneColor)

            if (pokemon.types.size > 1) {
                val typeTwoColor = getPokemonTypeColor(requireContext(), pokemon.types[1])

                binding.pokemonDetailsSecondType.typeText.text = pokemon.types[1]
                binding.pokemonDetailsSecondType.typeText.setTextColor(
                    getTextColorByBackgroundColor(
                        requireContext(),
                        typeTwoColor
                    )
                )
                binding.pokemonDetailsSecondType.typeBackground.setColorFilter(typeTwoColor)
            } else {
                binding.pokemonDetailsSecondType.typeContainer.visibility = View.GONE
            }
        } else {
            binding.pokemonDetailsFirstType.typeContainer.visibility = View.GONE
            binding.pokemonDetailsSecondType.typeContainer.visibility = View.GONE
        }
    }

    private fun refreshPokemonAbout(binding: FragmentPokedexDetailsBinding, pokemon: PokemonModel) {
        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)

        binding.pokemonDetailsDescriptionText.text =
            formatPocketdexObjectDescription(pokemon.flavor)

        binding.pokemonDetailsAbout.pokemonDetailsBar1.setColorFilter(pokemonColor)
        binding.pokemonDetailsAbout.pokemonDetailsBar2.setColorFilter(pokemonColor)

        binding.pokemonDetailsAbout.speciesValue.text = formatPokemonGenus(pokemon.genus)

        binding.pokemonDetailsAbout.heightValue.text = formatPokemonHeight(pokemon.height)

        binding.pokemonDetailsAbout.weightValue.text = formatPokemonWeight(pokemon.weight)
    }

    private fun refreshPokemonStats(binding: FragmentPokedexDetailsBinding, pokemon: PokemonModel) {
        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)

        binding.pokemonDetailsStats.baseStatsFixedText.setTextColor(pokemonColor)

        binding.pokemonDetailsStats.hpValue.text = pokemon.hp.toString()
        binding.pokemonDetailsStats.attackValue.text = pokemon.attack.toString()
        binding.pokemonDetailsStats.defenseValue.text = pokemon.defense.toString()
        binding.pokemonDetailsStats.spAttackValue.text = pokemon.specialAttack.toString()
        binding.pokemonDetailsStats.spDefenseValue.text = pokemon.specialDefense.toString()
        binding.pokemonDetailsStats.speedValue.text = pokemon.speed.toString()
    }
}