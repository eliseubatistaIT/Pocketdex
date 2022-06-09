package com.eliseubatista.pocketdex.views

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eliseubatista.pocketdex.databinding.ItemPokemonTypeSmallBinding
import com.eliseubatista.pocketdex.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class PokemonTypeSmallAdapter :
    ListAdapter<String, PokemonTypeSmallAdapter.ViewHolder>(TypeSmallDiffCallback()) {

    //lateinit var onTypeSmalClickedListener: OnTypeSmalClickedListener

    //Creates the view holder
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PokemonTypeSmallAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPokemonTypeSmallBinding.inflate(layoutInflater, parent, false)
        return PokemonTypeSmallAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonTypeSmallAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        //holder.bind(onTypeSmalClickedListener, item)
        holder.bind(item)
    }

    //Private constructor, i dont want to call it by accident, i want to use the from function
    class ViewHolder(val binding: ItemPokemonTypeSmallBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //This is used to bind the data to the view holder
        //fun bind(clickedListener: OnTypeSmalClickedListener, item: String) {
        fun bind(item: String) {

            val logo = getPokemonTypeLogoImage(itemView.context, item)
            binding.typeLogo.setImageDrawable(logo)
            binding.executePendingBindings()
        }
    }


}

//This util is used to avoid redrawing an item that was not changed
class TypeSmallDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ): Boolean {
        return oldItem == newItem
    }

}

/*
interface OnTypeSmalClickedListener {
    fun onTypeSmallClicked(pokemonName: String)
}

*/