import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.*
import com.eliseubatista.pocketdex.fragments.pokemon.PokemonDetailsViewModel
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.utils.*
import com.eliseubatista.pocketdex.views.PokemonEvolutionChainAdapter
import com.eliseubatista.pocketdex.views.PokemonTypeSmallAdapter

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
            ).get(PokemonDetailsViewModel::class.java)

        setupRecyclerViews(binding)

        viewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer { pokemon -> refreshPokemonEvolutions(binding, pokemon) })

        return binding.root
    }

    private fun setupRecyclerViews(binding: FragmentPokedexDetailsEvolutionsBinding) {

        evolutionChainAdapter = PokemonEvolutionChainAdapter()
        binding.pokemonDetailsEvolutions.gridView.layoutManager = LinearLayoutManager(context)
        binding.pokemonDetailsEvolutions.gridView.adapter = evolutionChainAdapter
    }

    private fun refreshPokemonEvolutions(
        binding: FragmentPokedexDetailsEvolutionsBinding,
        pokemon: PokemonModel
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