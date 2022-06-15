package com.eliseubatista.pocketdex.fragments.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.database.items.DatabaseItems
import com.eliseubatista.pocketdex.databinding.FragmentItemDetailsAboutBinding
import com.eliseubatista.pocketdex.utils.*

class ItemDetailsAboutFragment : Fragment() {

    private var itemName = ""
    private lateinit var viewModel: ItemDetailsViewModel
    private lateinit var viewModelFactory: ItemDetailsViewModel.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentItemDetailsAboutBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_item_details_about,
                container,
                true
            )

        viewModelFactory =
            ItemDetailsViewModel.Factory(requireActivity().application, itemName)
        viewModel =
            ViewModelProvider(
                requireParentFragment(),
                viewModelFactory
            )[ItemDetailsViewModel::class.java]

        viewModel.item.observe(
            viewLifecycleOwner
        ) { item -> refreshItemAbout(binding, item) }

        return binding.root
    }


    private fun refreshItemAbout(
        binding: FragmentItemDetailsAboutBinding,
        item: DatabaseItems
    ) {
        val itemColor = getItemBackgroundColor(requireContext(), item.id)

        binding.itemDetailsDescriptionText.text =
            formatPocketdexObjectDescription(item.flavor)

        binding.itemDetailsCategory.text.text = item.category
        binding.itemDetailsCategory.background.setColorFilter(itemColor)
        binding.itemDetailsCategory.background.alpha = 1.0f

    }
}