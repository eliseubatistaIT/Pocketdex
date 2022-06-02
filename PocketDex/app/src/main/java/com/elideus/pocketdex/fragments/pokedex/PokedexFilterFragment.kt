package com.elideus.pocketdex.fragments.pokedex

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.elideus.pocketdex.R
import com.elideus.pocketdex.databinding.FragmentPokedexFilterBinding

class PokedexFilterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentPokedexFilterBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_pokedex_filter, container, false)

        //setHasOptionsMenu(true)

        return binding.root
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_menu, menu)

        menu.ti

        requireActivity().actionBar!!.setHomeButtonEnabled(false)
        requireActivity().actionBar!!.setDisplayHomeAsUpEnabled(false)
        requireActivity().actionBar!!.setDisplayShowHomeEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.filter_apply) {
            requireView().findNavController().navigate(R.id.action_pokedexFilterFragment_to_navigation_pokedex)
        }

        return super.onOptionsItemSelected(item)
    }
    */


}