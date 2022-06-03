package com.elideus.pocketdex.fragments.pokedex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.elideus.pocketdex.R
import com.elideus.pocketdex.databinding.FragmentPokedexBinding
import com.elideus.pocketdex.databinding.FragmentPokemonDetailsBinding
import com.elideus.pocketdex.utils.formatPocketdexObjectName

class PokemonDetailsFragment : Fragment() {

    private lateinit var pokemonName: String
    private lateinit var viewModel: PokemonDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokemonDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokemon_details, container, false)

        viewModel = ViewModelProvider(this).get(PokemonDetailsViewModel::class.java)

        val args = PokemonDetailsFragmentArgs.fromBundle(requireArguments())

        pokemonName = args.pokemonName

        (activity as AppCompatActivity).supportActionBar?.title =
            "${formatPocketdexObjectName(pokemonName)} details"

        return binding.root
    }
}