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
import com.eliseubatista.pocketdex.databinding.FragmentPokedexDetailsAboutBinding
import com.eliseubatista.pocketdex.databinding.FragmentPokedexDetailsBinding
import com.eliseubatista.pocketdex.fragments.pokemon.PokemonDetailsViewModel
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.utils.*
import com.eliseubatista.pocketdex.views.PokemonEvolutionChainAdapter
import com.eliseubatista.pocketdex.views.PokemonTypeSmallAdapter

class PokedexDetailsAboutFragment : Fragment() {

    private var pokemonName = ""
    private lateinit var viewModel: PokemonDetailsViewModel
    private lateinit var viewModelFactory: PokemonDetailsViewModel.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokedexDetailsAboutBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_pokedex_details_about,
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

        viewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer { pokemon -> refreshPokemonAbout(binding, pokemon) })

        return binding.root
    }


    private fun refreshPokemonAbout(
        binding: FragmentPokedexDetailsAboutBinding,
        pokemon: PokemonModel
    ) {
        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)

        binding.pokemonDetailsDescriptionText.text =
            formatPocketdexObjectDescription(pokemon.flavor)

        binding.pokemonDetailsAbout.pokemonDetailsBar1.setColorFilter(pokemonColor)
        binding.pokemonDetailsAbout.pokemonDetailsBar2.setColorFilter(pokemonColor)

        binding.pokemonDetailsAbout.speciesValue.text = formatPokemonGenus(pokemon.genus)

        binding.pokemonDetailsAbout.heightValue.text = formatPokemonHeight(pokemon.height)

        binding.pokemonDetailsAbout.weightValue.text = formatPokemonWeight(pokemon.weight)

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
}