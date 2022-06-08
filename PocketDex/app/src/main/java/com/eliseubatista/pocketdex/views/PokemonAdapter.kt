package com.eliseubatista.pocketdex.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.ItemPokemonListBinding
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class PokemonAdapter :
    ListAdapter<PokemonModel, PokemonAdapter.ViewHolder>(PokemonDiffCallback()) {

    private var coroutineJob = Job()
    private val coroutineScope = CoroutineScope(coroutineJob + Dispatchers.Main)

    lateinit var onPokemonClickedListener: OnPokemonClickedListener

    //Creates the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonListBinding.inflate(layoutInflater, parent, false)
        return PokemonAdapter.ViewHolder(binding)
    }

    //Binds a new item to the view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(onPokemonClickedListener, item)
    }

    //Private constructor, i dont want to call it by accident, i want to use the from function
    class ViewHolder(val binding: ItemPokemonListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //This is used to bind the data to the view holder
        fun bind(clickedListener: OnPokemonClickedListener, item: PokemonModel) {

            val imageScale = getImageScaleByEvolutionChain(item.name, item.evolutionChain)

            binding.pokemonListImage.scaleX = imageScale
            binding.pokemonListImage.scaleY = imageScale

            loadImageWithGlide(item.maleSprite, binding.pokemonListImage)

            val pokemonColor = getPokemonBackgroundColor(itemView.context, item.color)

            binding.pokemonListItemBackground.background.setTint(pokemonColor)

            binding.pokemonListId.text = "#${item.id}"
            binding.pokemonListId.setTextColor(
                getTextColorByBackgroundColor(
                    itemView.context,
                    pokemonColor
                )
            )

            binding.pokemonListName.text = item.name
            binding.pokemonListName.setTextColor(
                getTextColorByBackgroundColor(
                    itemView.context,
                    pokemonColor
                )
            )

            if (item.types.size > 0) {
                val typeOneColor = getPokemonTypeColor(itemView.context, item.types[0])

                binding.pokemonListFirstType.typeText.text = item.types[0]
                binding.pokemonListFirstType.typeText.setTextColor(
                    getTextColorByBackgroundColor(
                        itemView.context,
                        typeOneColor
                    )
                )

                binding.pokemonListFirstType.typeBackground.setColorFilter(typeOneColor)

                if (item.types.size > 1) {
                    val typeTwoColor = getPokemonTypeColor(itemView.context, item.types[1])

                    binding.pokemonListSecondType.typeText.text = item.types[1]
                    binding.pokemonListSecondType.typeText.setTextColor(
                        getTextColorByBackgroundColor(
                            itemView.context,
                            typeTwoColor
                        )
                    )
                    binding.pokemonListSecondType.typeBackground.setColorFilter(typeTwoColor)
                } else {
                    binding.pokemonListSecondType.typeContainer.visibility = View.GONE
                }
            } else {
                binding.pokemonListFirstType.typeContainer.visibility = View.GONE
                binding.pokemonListSecondType.typeContainer.visibility = View.GONE
            }

            binding.pokemonListItemBackground.setOnClickListener { v: View ->
                clickedListener.onPokemonClicked(
                    item.name
                )
            }

            binding.executePendingBindings()
        }
    }
}

//This util is used to avoid redrawing an item that was not changed
class PokemonDiffCallback : DiffUtil.ItemCallback<PokemonModel>() {
    override fun areItemsTheSame(
        oldItem: PokemonModel,
        newItem: PokemonModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PokemonModel,
        newItem: PokemonModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

}

interface OnPokemonClickedListener {
    fun onPokemonClicked(pokemonName: String)
}