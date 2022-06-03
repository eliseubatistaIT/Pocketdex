package com.elideus.pocketdex.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elideus.pocketdex.R
import com.elideus.pocketdex.models.PokemonModel
import com.elideus.pocketdex.network.BaseNameAndUrl
import com.elideus.pocketdex.network.PokemonDetailedData
import com.elideus.pocketdex.utils.getPokemonBackgroundColor
import com.elideus.pocketdex.utils.getPokemonTextColor
import com.elideus.pocketdex.utils.loadImageWithGlide
import com.elideus.pocketdex.utils.manipulateColor


class PokemonListAdapter :
    ListAdapter<PokemonModel, PokemonListAdapter.ViewHolder>(PokemonDiffCallback()) {

    //Creates the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    //Binds a new item to the view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    //Private constructor, i dont want to call it by accident, i want to use the from function
    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val pokemonBackground: ImageView =
            itemView.findViewById(R.id.pokemon_list_item_background)
        private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
        private val pokemonId: TextView = itemView.findViewById(R.id.pokemon_id)
        private val pokemonTypeText: TextView = itemView.findViewById(R.id.pokemon_type_text)
        private val pokemonTypeImage: ImageView =
            itemView.findViewById(R.id.pokemon_type_background)


        companion object {
            //This is used to create the view holder in a cleaner way
            fun from(parent: ViewGroup): ViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pokemon_list, parent, false)
                return ViewHolder(view)
            }
        }

        //This is used to bind the data to the view holder
        fun bind(item: PokemonModel) {

            var type = "unknown"

            if (item.types.isNotEmpty()) {
                type = item.types[0].name
            }

            val pokemonColor = getPokemonBackgroundColor(itemView.context, type)
            val pokemonDarkerColor = manipulateColor(pokemonColor, 0.75f)

            pokemonName.text = item.name
            pokemonName.setTextColor(getPokemonTextColor(itemView.context, type))
            pokemonId.text = "#${item.id}"
            pokemonTypeText.text = type
            pokemonTypeImage.setColorFilter(pokemonDarkerColor)
            pokemonBackground.setColorFilter(pokemonColor)
            loadImageWithGlide(item.maleSprite, pokemonImage)
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

}