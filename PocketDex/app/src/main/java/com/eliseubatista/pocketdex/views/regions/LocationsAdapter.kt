package com.eliseubatista.pocketdex.views.regions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eliseubatista.pocketdex.database.regions.DatabaseLocation
import com.eliseubatista.pocketdex.databinding.ItemRegionListBinding
import com.eliseubatista.pocketdex.utils.formatPocketdexObjectName
import com.eliseubatista.pocketdex.utils.getLocationRegionImage

class LocationsAdapter :
    ListAdapter<DatabaseLocation, LocationsAdapter.ViewHolder>(LocationDiffCallback()) {

    lateinit var onLocationClickedListener: OnLocationClickedListener

    //Creates the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRegionListBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    //Binds a new item to the view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(onLocationClickedListener, item)
    }

    class ViewHolder(val binding: ItemRegionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        //This is used to bind the data to the view holder
        fun bind(clickedListener: OnLocationClickedListener, location: DatabaseLocation) {

            val regionImage = getLocationRegionImage(itemView.context, location.name)

            binding.locationRegionImage.setImageDrawable(regionImage)

            binding.locationListId.text.text = "#${location.id}"

            binding.locationListName.text.text = formatPocketdexObjectName(location.name)

            binding.locationRegionImage.setOnClickListener {
                clickedListener.onLocationClicked(
                    location.name
                )
            }

            binding.executePendingBindings()
        }
    }
}

//This util is used to avoid redrawing an item that was not changed
class LocationDiffCallback : DiffUtil.ItemCallback<DatabaseLocation>() {
    override fun areItemsTheSame(
        oldItem: DatabaseLocation,
        newItem: DatabaseLocation
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DatabaseLocation,
        newItem: DatabaseLocation
    ): Boolean {
        return oldItem.id == newItem.id
    }

}

interface OnLocationClickedListener {
    fun onLocationClicked(name: String)
}