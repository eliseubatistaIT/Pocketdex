package com.elideus.pocketdex.views

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
import com.elideus.pocketdex.utils.*


class PokemonAdapter :
    ListAdapter<PokemonModel, PokemonAdapter.ViewHolder>(PokemonDiffCallback()) {

    lateinit var onPokemonClickedListener: OnPokemonClickedListener

    //Creates the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_list, parent, false)
        return ViewHolder(view, onPokemonClickedListener)
    }

    //Binds a new item to the view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    //Private constructor, i dont want to call it by accident, i want to use the from function
    class ViewHolder(
        itemView: View,
        private val onPokemonClickedListener: OnPokemonClickedListener
    ) : RecyclerView.ViewHolder(itemView) {

        private val pokemonBackground: ImageView =
            itemView.findViewById(R.id.pokemon_list_item_background)
        private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_image)
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_name)
        private val pokemonId: TextView = itemView.findViewById(R.id.pokemon_id)
        private val pokemonTypeText: TextView = itemView.findViewById(R.id.pokemon_type_text)
        private val pokemonTypeImage: ImageView =
            itemView.findViewById(R.id.pokemon_type_background)


        //This is used to bind the data to the view holder
        fun bind(item: PokemonModel) {

            var type = "unknown"

            if (item.types.isNotEmpty()) {
                type = item.types[0].name
            }

            val pokemonColor = getPokemonBackgroundColor(itemView.context, type)
            val pokemonDarkerColor = manipulateColor(pokemonColor, 0.75f)

            pokemonName.text = formatPocketdexObjectName(item.name)
            pokemonName.setTextColor(getTextColorByBackgroundColor(itemView.context, pokemonColor))
            pokemonId.text = "#${item.id}"
            pokemonTypeText.text = type
            pokemonTypeText.setTextColor(
                getTextColorByBackgroundColor(
                    itemView.context,
                    pokemonDarkerColor
                )
            )
            pokemonTypeImage.setColorFilter(pokemonDarkerColor)
            pokemonBackground.setColorFilter(pokemonColor)
            loadImageWithGlide(item.maleSprite, pokemonImage)

            pokemonBackground.setOnClickListener { v: View ->
                onPokemonClickedListener.onPokemonClicked(
                    item.name
                )
            }
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