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
import com.eliseubatista.pocketdex.models.pokemons.PokemonModel
import com.eliseubatista.pocketdex.utils.getPokemonBackgroundColor
import com.eliseubatista.pocketdex.utils.getPokemonTypeColor
import com.eliseubatista.pocketdex.utils.getTextColorByBackgroundColor
import com.eliseubatista.pocketdex.utils.loadImageWithGlide

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
        private val pokemonImage: ImageView = itemView.findViewById(R.id.pokemon_list_image)
        private val pokemonName: TextView = itemView.findViewById(R.id.pokemon_list_name)
        private val pokemonId: TextView = itemView.findViewById(R.id.pokemon_list_id)
        private val pokemonFirstTypeContainer: FrameLayout =
            itemView.findViewById(R.id.pokemon_list_first_type)
        private val pokemonFirstTypeText: TextView =
            itemView.findViewById(R.id.pokemon_list_first_type_text)
        private val pokemonFirstTypeImage: ImageView =
            itemView.findViewById(R.id.pokemon_list_first_type_background)
        private val pokemonSecondTypeContainer: FrameLayout =
            itemView.findViewById(R.id.pokemon_list_second_type)
        private val pokemonSecondTypeText: TextView =
            itemView.findViewById(R.id.pokemon_list_second_type_text)
        private val pokemonSecondTypeImage: ImageView =
            itemView.findViewById(R.id.pokemon_list_second_type_background)

        //This is used to bind the data to the view holder
        fun bind(item: PokemonModel) {

            loadImageWithGlide(item.maleSprite, pokemonImage)

            val pokemonColor = getPokemonBackgroundColor(itemView.context, item.species.color)

            pokemonBackground.setColorFilter(pokemonColor)

            pokemonId.text = "#${item.id}"
            pokemonId.setTextColor(
                getTextColorByBackgroundColor(
                    itemView.context,
                    pokemonColor
                )
            )

            pokemonName.text = item.name
            pokemonName.setTextColor(
                getTextColorByBackgroundColor(
                    itemView.context,
                    pokemonColor
                )
            )

            if (item.types.size > 0) {
                val typeOneColor = getPokemonTypeColor(itemView.context, item.types[0].name)

                pokemonFirstTypeText.text = item.types[0].name
                pokemonFirstTypeText.setTextColor(
                    getTextColorByBackgroundColor(
                        itemView.context,
                        typeOneColor
                    )
                )

                pokemonFirstTypeImage.setColorFilter(typeOneColor)

                if (item.types.size > 1) {
                    val typeTwoColor = getPokemonTypeColor(itemView.context, item.types[1].name)

                    pokemonSecondTypeText.text = item.types[1].name
                    pokemonSecondTypeText.setTextColor(
                        getTextColorByBackgroundColor(
                            itemView.context,
                            typeTwoColor
                        )
                    )
                    pokemonSecondTypeImage.setColorFilter(typeTwoColor)
                } else {
                    pokemonSecondTypeContainer.visibility = View.GONE
                }
            } else {
                pokemonFirstTypeContainer.visibility = View.GONE
                pokemonSecondTypeContainer.visibility = View.GONE
            }

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