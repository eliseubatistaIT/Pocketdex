package com.eliseubatista.pocketdex.views.pokemons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eliseubatista.pocketdex.databinding.ItemPokemonEvolutionListBinding
import com.eliseubatista.pocketdex.models.pokemons.EvolutionChainModel
import com.eliseubatista.pocketdex.utils.getImageScaleByEvolutionChain
import com.eliseubatista.pocketdex.utils.loadImageWithGlide

class PokemonEvolutionChainAdapter :
    ListAdapter<EvolutionChainModel, PokemonEvolutionChainAdapter.ViewHolder>(
        PokemonEvolutionsDiffCallback()
    ) {

    //Creates the view holder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonEvolutionListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(val binding: ItemPokemonEvolutionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //This is used to bind the data to the view holder
        fun bind(item: EvolutionChainModel) {

            when (item.evolutions.size) {
                1 -> {
                    binding.evolutionOfTwoContainer.visibility = View.GONE
                    binding.evolutionOfThreeContainer.visibility = View.GONE
                }
                2 -> {
                    binding.evolutionOfTwoContainer.visibility = View.VISIBLE
                    binding.evolutionOfThreeContainer.visibility = View.GONE
                }
                else -> {
                    binding.evolutionOfTwoContainer.visibility = View.GONE
                    binding.evolutionOfThreeContainer.visibility = View.VISIBLE
                }
            }

            for ((index, evo) in item.evolutions.withIndex()) {
                val imageViewOfTwo = when (index) {
                    0 -> binding.evolutionOfTwo.baseForm
                    else -> binding.evolutionOfTwo.evolution
                }

                val imageViewOfThree = when (index) {
                    0 -> binding.evolutionOfThree.baseForm
                    1 -> binding.evolutionOfThree.evolution1
                    else -> binding.evolutionOfThree.evolution2
                }

                val imageScale = getImageScaleByEvolutionChain(
                    evo.name,
                    evo.evolutionChain
                )

                imageViewOfTwo.scaleX = imageScale
                imageViewOfTwo.scaleY = imageScale

                imageViewOfThree.scaleX = imageScale
                imageViewOfThree.scaleY = imageScale

                loadImageWithGlide(evo.spriteUrl, imageViewOfTwo)
                loadImageWithGlide(evo.spriteUrl, imageViewOfThree)
            }

            binding.executePendingBindings()
        }
    }


}

//This util is used to avoid redrawing an item that was not changed
class PokemonEvolutionsDiffCallback : DiffUtil.ItemCallback<EvolutionChainModel>() {
    override fun areItemsTheSame(
        oldItem: EvolutionChainModel,
        newItem: EvolutionChainModel
    ): Boolean {
        return oldItem.chainString == newItem.chainString
    }

    override fun areContentsTheSame(
        oldItem: EvolutionChainModel,
        newItem: EvolutionChainModel
    ): Boolean {
        return oldItem.chainString == newItem.chainString
    }

}
