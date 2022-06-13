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
import com.eliseubatista.pocketdex.databinding.FragmentPokedexDetailsStatsBinding
import com.eliseubatista.pocketdex.fragments.pokemon.PokemonDetailsViewModel
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.models.pokemons.TypeModel
import com.eliseubatista.pocketdex.utils.*
import com.eliseubatista.pocketdex.views.PokemonEvolutionChainAdapter
import com.eliseubatista.pocketdex.views.PokemonTypeSmallAdapter

class PokedexDetailsStatsFragment : Fragment() {

    private var pokemonName = ""
    private lateinit var viewModel: PokemonDetailsViewModel
    private lateinit var viewModelFactory: PokemonDetailsViewModel.Factory

    private lateinit var defenseDoubleDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var defenseHalfDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var defenseNoDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var attackDoubleDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var attackHalfDamageAdapter: PokemonTypeSmallAdapter
    private lateinit var attackNoDamageAdapter: PokemonTypeSmallAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokedexDetailsStatsBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_pokedex_details_stats,
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
            Observer { pokemon -> refreshPokemonStats(binding, pokemon, viewModel.pokeFirstType) })

        return binding.root
    }

    private fun setupRecyclerViews(binding: FragmentPokedexDetailsStatsBinding) {

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
    }

    private fun refreshPokemonStats(
        binding: FragmentPokedexDetailsStatsBinding,
        pokemon: PokemonModel,
        type: TypeModel
    ) {
        val pokemonColor = getPokemonBackgroundColor(requireContext(), pokemon.color)

        binding.pokemonDetailsStats.baseStatsFixedText.setTextColor(pokemonColor)

        binding.pokemonDetailsStats.hpValue.text = pokemon.hp.toString()
        binding.pokemonDetailsStats.attackValue.text = pokemon.attack.toString()
        binding.pokemonDetailsStats.defenseValue.text = pokemon.defense.toString()
        binding.pokemonDetailsStats.spAttackValue.text = pokemon.specialAttack.toString()
        binding.pokemonDetailsStats.spDefenseValue.text = pokemon.specialDefense.toString()
        binding.pokemonDetailsStats.speedValue.text = pokemon.speed.toString()

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
}