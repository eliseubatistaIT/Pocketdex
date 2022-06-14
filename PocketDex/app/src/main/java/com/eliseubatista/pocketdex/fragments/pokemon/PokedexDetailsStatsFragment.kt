import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
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

        viewModel.pokemon.observe(
            viewLifecycleOwner,
            Observer { pokemon -> refreshPokemonData(binding, pokemon, viewModel.pokeFirstType) })

        return binding.root
    }

    private fun refreshPokemonData(
        binding: FragmentPokedexDetailsStatsBinding,
        pokemon: PokemonModel,
        type: TypeModel
    ) {
        refreshPokemonStats(binding, pokemon, type)
        refreshDamageRelations(binding, pokemon, type)
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
    }


    private fun refreshDamageRelations(
        binding: FragmentPokedexDetailsStatsBinding,
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

        addDamageRelations(
            binding,
            type.doubleDamageFrom,
            binding.pokemonDetailsDamages.defenseDoubleDamageContainer.typesGrid
        )
        addDamageRelations(
            binding,
            type.halfDamageFrom,
            binding.pokemonDetailsDamages.defenseHalfDamageContainer.typesGrid
        )
        addDamageRelations(
            binding,
            type.noDamageFrom,
            binding.pokemonDetailsDamages.defenseNoDamageContainer.typesGrid
        )
        addDamageRelations(
            binding,
            type.doubleDamageTo,
            binding.pokemonDetailsDamages.attackDoubleDamageContainer.typesGrid
        )
        addDamageRelations(
            binding,
            type.halfDamageTo,
            binding.pokemonDetailsDamages.attackHalfDamageContainer.typesGrid
        )
        addDamageRelations(
            binding,
            type.noDamageTo,
            binding.pokemonDetailsDamages.attackNoDamageContainer.typesGrid
        )

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

    private fun addDamageRelations(
        binding: FragmentPokedexDetailsStatsBinding,
        damageRelations: List<String>,
        layout: GridLayout
    ) {
        for (relation in damageRelations) {
            val image = ImageView(requireContext())
            val imageSize = dpToPx(requireContext(), 30)
            val marginSize = dpToPx(requireContext(), 3)

            val layoutParams = ViewGroup.MarginLayoutParams(imageSize, imageSize)
            layoutParams.setMargins(marginSize, marginSize, marginSize, marginSize)
            image.layoutParams = layoutParams

            val pokeTypeLogo = getPokemonTypeLogoImage(requireContext(), relation)
            image.setImageDrawable(pokeTypeLogo)

            layout.addView(image)
        }
    }
}