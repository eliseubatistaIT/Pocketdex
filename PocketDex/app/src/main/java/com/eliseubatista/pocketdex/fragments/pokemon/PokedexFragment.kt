package com.eliseubatista.pocketdex.fragments.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.FragmentPokedexBinding
import com.eliseubatista.pocketdex.views.OnPokemonClickedListener
import com.eliseubatista.pocketdex.views.PokemonAdapter

class PokedexFragment : Fragment() {

    private lateinit var viewModel: PokedexViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokedexBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex, container, false)

        (activity as AppCompatActivity).supportActionBar?.title = "Pokédex"

        viewModel = ViewModelProvider(this).get(PokedexViewModel::class.java)

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
        viewModel.listOfPokemons.observe(viewLifecycleOwner, Observer {
            it?.let {
                pokemonListAdapter.submitList(it)
            }
        })

        //Scroll listenner
        binding.pokemonsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                //If we reach the end of the list, we want to show more pokemons
                //TODO: Verificar scroll
                if (recyclerView.canScrollVertically(3) == false) {
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

        fragmentTransaction.add(R.id.fragment_container, pokedexDetailsFragment)
        fragmentTransaction.addToBackStack("BACKSTACK")
        fragmentTransaction.commit()
    }


}