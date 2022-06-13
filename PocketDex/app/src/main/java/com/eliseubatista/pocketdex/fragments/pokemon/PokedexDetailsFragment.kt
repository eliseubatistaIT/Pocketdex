package com.eliseubatista.pocketdex.fragments.pokemon

import android.os.Bundle
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
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.FragmentPokedexDetailsBinding
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.utils.*
import com.eliseubatista.pocketdex.views.OnPokemonClickedListener
import com.eliseubatista.pocketdex.views.PokemonAdapter
import com.eliseubatista.pocketdex.views.PokemonEvolutionChainAdapter
import com.eliseubatista.pocketdex.views.PokemonTypeSmallAdapter

class PokedexDetailsFragment : Fragment() {

    private var pokemonName = ""
    private lateinit var viewModel: PokemonDetailsViewModel
    private lateinit var viewModelFactory: PokemonDetailsViewModel.Factory

    private lateinit var defenseDoubleDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var defenseHalfDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var defenseNoDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var attackDoubleDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var attackHalfDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var attackNoDamageAdapter: PokemonTypeSmallAdapter

    private lateinit var evolutionChainAdapter: PokemonEvolutionChainAdapter


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

        setupRecyclerViews(binding)

        viewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer { pokemon -> refreshPokemonDisplay(binding, pokemon) })

        return binding.root
    }

    private fun setupRecyclerViews(binding: FragmentPokedexDetailsBinding) {

        defenseDoubleDamageAdapter = PokemonTypeSmallAdapter()
        binding.pokemonDetailsDamages.defenseDoubleDamageContainer.gridView.layoutManager =
            GridLayoutManager(context, 8)
        binding.pokemonDetailsDamages.defenseDoubleDamageContainer.gridView.adapter =
            defenseDoubleDamageAdapter

        defenseHalfDamageAdapter = PokemonTypeSmallAdapter()
        binding.pokemonDetailsDamages.defenseHalfDamageContainer.gridView.layoutManager =
            GridLayoutManager(context, 8)
        binding.pokemonDetailsDamages.defenseHalfDamageContainer.gridView.adapter =
            defenseHalfDamageAdapter

        defenseNoDamageAdapter = PokemonTypeSmallAdapter()
        binding.pokemonDetailsDamages.defenseNoDamageContainer.gridView.layoutManager =
            GridLayoutManager(context, 8)
        binding.pokemonDetailsDamages.defenseNoDamageContainer.gridView.adapter =
            defenseNoDamageAdapter

        attackDoubleDamageAdapter = PokemonTypeSmallAdapter()
        binding.pokemonDetailsDamages.attackDoubleDamageContainer.gridView.layoutManager =
            GridLayoutManager(context, 8)
        binding.pokemonDetailsDamages.attackDoubleDamageContainer.gridView.adapter =
            attackDoubleDamageAdapter

        attackHalfDamageAdapter = PokemonTypeSmallAdapter()
        binding.pokemonDetailsDamages.attackHalfDamageContainer.gridView.layoutManager =
            GridLayoutManager(context, 8)
        binding.pokemonDetailsDamages.attackHalfDamageContainer.gridView.adapter =
            attackHalfDamageAdapter

        attackNoDamageAdapter = PokemonTypeSmallAdapter()
        binding.pokemonDetailsDamages.attackNoDamageContainer.gridView.layoutManager =
            GridLayoutManager(context, 8)
        binding.pokemonDetailsDamages.attackNoDamageContainer.gridView.adapter =
            attackNoDamageAdapter

        //----------------------------------------------------

        evolutionChainAdapter = PokemonEvolutionChainAdapter()
        binding.pokemonDetailsEvolutions.gridView.layoutManager = LinearLayoutManager(context)
        binding.pokemonDetailsEvolutions.gridView.adapter = evolutionChainAdapter
    }

    private fun refreshPokemonDisplay(
        binding: FragmentPokedexDetailsBinding,
        pokemon: PokemonModel
    ) {
        refreshPokemonHeader(binding, pokemon)
        refreshPokemonAbout(binding, pokemon)
        refreshPokemonStats(binding, pokemon)
        refreshPokemonDamages(binding, pokemon, viewModel.pokeFirstType)
        refreshPokemonEvolutions(binding, pokemon)
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

        when (pokemon.types.size) {
            1 -> {

            }
            2 -> {

            }
        }

        val typeOneLogo = getPokemonTypeLogoImage(requireContext(), pokemon.types[0])
        val typeOneTextImage = getPokemonTypeTextImage(requireContext(), pokemon.types[0])

        binding.pokemonDetailsTypes.onlyTypeValue.typeLogo.setImageDrawable(typeOneLogo)
        binding.pokemonDetailsTypes.firstTypeValue.typeLogo.setImageDrawable(typeOneLogo)

        binding.pokemonDetailsTypes.onlyTypeValue.typeTextImage.setImageDrawable(typeOneTextImage)
        binding.pokemonDetailsTypes.firstTypeValue.typeTextImage.setImageDrawable(typeOneTextImage)

        if (pokemon.types.size > 1) {

            val typeTwoLogo = getPokemonTypeLogoImage(requireContext(), pokemon.types[1])
            val typeTwoTextImage = getPokemonTypeTextImage(requireContext(), pokemon.types[1])

            binding.pokemonDetailsTypes.secondTypeValue.typeLogo.setImageDrawable(typeTwoLogo)
            binding.pokemonDetailsTypes.secondTypeValue.typeTextImage.setImageDrawable(
                typeTwoTextImage
            )

            binding.pokemonDetailsTypes.onlyTypeContainer.visibility = View.GONE
            binding.pokemonDetailsTypes.firstTypeContainer.visibility = View.VISIBLE
            binding.pokemonDetailsTypes.secondTypeContainer.visibility = View.VISIBLE
        } else {
            binding.pokemonDetailsTypes.onlyTypeContainer.visibility = View.VISIBLE
            binding.pokemonDetailsTypes.firstTypeContainer.visibility = View.GONE
            binding.pokemonDetailsTypes.secondTypeContainer.visibility = View.GONE
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

    private fun refreshPokemonDamages(
        binding: FragmentPokedexDetailsBinding,
        pokemon: PokemonModel,
        type: TypeModel
    ) {
        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)

        binding.pokemonDetailsDamages.attackFixedText.setTextColor(pokemonColor)
        binding.pokemonDetailsDamages.defensesFixedText.setTextColor(pokemonColor)

        binding.pokemonDetailsDamages.defenseDoubleDamageContainer.fixedText.text = "0%"
        binding.pokemonDetailsDamages.defenseHalfDamageContainer.fixedText.text = "50%"
        binding.pokemonDetailsDamages.defenseNoDamageContainer.fixedText.text = "100%"
        binding.pokemonDetailsDamages.attackNoDamageContainer.fixedText.text = "0%"
        binding.pokemonDetailsDamages.attackHalfDamageContainer.fixedText.text = "50%"
        binding.pokemonDetailsDamages.attackDoubleDamageContainer.fixedText.text = "100%"

        defenseDoubleDamageAdapter.submitList(type.doubleDamageFrom)
        defenseHalfDamageAdapter.submitList(type.halfDamageFrom)
        defenseNoDamageAdapter.submitList(type.noDamageFrom)
        attackDoubleDamageAdapter.submitList(type.doubleDamageTo)
        attackHalfDamageAdapter.submitList(type.halfDamageTo)
        attackNoDamageAdapter.submitList(type.noDamageTo)

        if (isPokemonDamageRelationEmpty(type.doubleDamageFrom)) {
            binding.pokemonDetailsDamages.defenseDoubleDamageContainer.damageTypesContainer.visibility =
                View.GONE
        }
        if (isPokemonDamageRelationEmpty(type.halfDamageFrom)) {
            binding.pokemonDetailsDamages.defenseHalfDamageContainer.damageTypesContainer.visibility =
                View.GONE
        }
        if (isPokemonDamageRelationEmpty(type.noDamageFrom)) {
            binding.pokemonDetailsDamages.defenseNoDamageContainer.damageTypesContainer.visibility =
                View.GONE
        }
        if (isPokemonDamageRelationEmpty(type.noDamageTo)) {
            binding.pokemonDetailsDamages.attackNoDamageContainer.damageTypesContainer.visibility =
                View.GONE
        }
        if (isPokemonDamageRelationEmpty(type.halfDamageTo)) {
            binding.pokemonDetailsDamages.attackHalfDamageContainer.damageTypesContainer.visibility =
                View.GONE
        }

        if (isPokemonDamageRelationEmpty(type.doubleDamageTo)) {
            binding.pokemonDetailsDamages.attackDoubleDamageContainer.damageTypesContainer.visibility =
                View.GONE
        }
    }

    private fun refreshPokemonEvolutions(
        binding: FragmentPokedexDetailsBinding,
        pokemon: PokemonModel
    ) {

        Log.i("CARAMBA", pokemon.evolutionChain.toString())

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