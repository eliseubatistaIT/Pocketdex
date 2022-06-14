package com.eliseubatista.pocketdex.fragments.pokemon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.FragmentPokedexBinding
import com.eliseubatista.pocketdex.utils.ProgressBarDialog
import com.eliseubatista.pocketdex.utils.replaceFragment
import com.eliseubatista.pocketdex.views.pokemons.OnPokemonClickedListener
import com.eliseubatista.pocketdex.views.pokemons.PokemonAdapter

class PokedexFragment : Fragment() {

    private lateinit var viewModel: PokedexViewModel
    private lateinit var viewModelFactory: PokedexViewModel.Factory
    private lateinit var progressBarDialog: ProgressBarDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokedexBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Pokédex"

        viewModelFactory = PokedexViewModel.Factory(requireActivity().application)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[PokedexViewModel::class.java]

        progressBarDialog = ProgressBarDialog(inflater, requireContext())

        setupLinearRecyclerView(binding)

        return binding.root
    }

    private fun setupLinearRecyclerView(binding: FragmentPokedexBinding) {

        //Create and add the layout manager
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.pokemonsList.layoutManager = linearLayoutManager

        //Create and bind the list adapter
        val pokemonListAdapter = PokemonAdapter()
        binding.pokemonsList.adapter = pokemonListAdapter

        //Click listener
        pokemonListAdapter.onPokemonClickedListener =
            object : OnPokemonClickedListener {
                override fun onPokemonClicked(pokemonName: String) {
                    navigateToPokedexDetails(pokemonName)
                }

            }

        //Create and observer to refresh the list automatically
        viewModel.pokemons.observe(viewLifecycleOwner) {
            pokemonListAdapter.submitList(it)
        }

        viewModel.isLoadingMorePokemons.observe(viewLifecycleOwner) {
            if (it) {
                progressBarDialog.startLoading("Loading More Pokemons..")
            } else {
                progressBarDialog.dismiss()
            }
        }

        //Scroll listener
        binding.pokemonsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                //If we reach the end of the list, we want to show more pokemons
                //TODO: Check scroll
                if (!recyclerView.canScrollVertically(3)) {
                    viewModel.getMorePokemons()
                }
            }
        })
    }

    private fun navigateToPokedexDetails(pokemonName: String) {

        val bundle = Bundle()
        bundle.putString("POKEMON_NAME", pokemonName)

        val pokedexDetailsFragment = PokedexDetailsFragment()
        pokedexDetailsFragment.arguments = bundle

        val fragmentTransaction = parentFragmentManager.beginTransaction()

        fragmentTransaction.replaceFragment(pokedexDetailsFragment)
    }


}