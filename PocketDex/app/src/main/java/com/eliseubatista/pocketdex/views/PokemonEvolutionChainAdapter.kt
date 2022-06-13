package com.eliseubatista.pocketdex.views

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
    ): PokemonEvolutionChainAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonEvolutionListBinding.inflate(layoutInflater, parent, false)
        return PokemonEvolutionChainAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonEvolutionChainAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    //Private constructor, i dont want to call it by accident, i want to use the from function
    class ViewHolder(val binding: ItemPokemonEvolutionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //This is used to bind the data to the view holder
        fun bind(item: EvolutionChainModel) {

            if (item.evolutions.size == 1) {
                binding.evolutionOfTwoContainer.visibility = View.GONE
                binding.evolutionOfThreeContainer.visibility = View.GONE
            } else if (item.evolutions.size == 2) {
                binding.evolutionOfTwoContainer.visibility = View.VISIBLE
                binding.evolutionOfThreeContainer.visibility = View.GONE
            } else {
                binding.evolutionOfTwoContainer.visibility = View.GONE
                binding.evolutionOfThreeContainer.visibility = View.VISIBLE
            }

            for ((index, evo) in item.evolutions.withIndex()) {
                var imageViewOfTwo = when (index) {
                    0 -> binding.evolutionOfTwo.baseForm
                    else -> binding.evolutionOfTwo.evolution
                }

                var imageViewOfThree = when (index) {
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

                loadImageWithGlide(evo.maleSprite, imageViewOfTwo)
                loadImageWithGlide(evo.maleSprite, imageViewOfThree)
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
