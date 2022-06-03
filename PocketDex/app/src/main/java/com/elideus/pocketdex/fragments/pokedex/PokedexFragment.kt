package com.elideus.pocketdex.fragments.pokedex

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elideus.pocketdex.R
import com.elideus.pocketdex.views.PokemonAdapter
import com.elideus.pocketdex.databinding.FragmentPokedexBinding
import com.elideus.pocketdex.views.OnPokemonClickedListener

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
                    view?.findNavController()
                        ?.navigate(
                            PokedexFragmentDirections.actionNavigationPokedexToPokemonDetailsFragment(
                                pokemonName
                            )
                        )
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
                if (recyclerView.canScrollVertically(3) == false) {
                    viewModel.getMorePokemons()
                }
            }
        })
    }


}