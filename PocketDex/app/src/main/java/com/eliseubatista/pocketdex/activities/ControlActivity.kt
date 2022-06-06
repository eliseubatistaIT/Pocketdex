package com.eliseubatista.pocketdex.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.ActivityControlBinding
import com.eliseubatista.pocketdex.fragments.items.ItemsFragment
import com.eliseubatista.pocketdex.fragments.locations.LocationsFragment
import com.eliseubatista.pocketdex.fragments.pokemon.PokedexFragment
import com.eliseubatista.pocketdex.fragments.profile.ProfileFragment
import com.eliseubatista.pocketdex.fragments.settings.SettingsFragment

class ControlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityControlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView<ActivityControlBinding>(this, R.layout.activity_control)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            onBottomNavViewItemSelected(
                item
            )
        }

        navigateToPokedex()
    }

    private fun onBottomNavViewItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_locations -> navigateToLocations()
            R.id.nav_items -> navigateToItems()
            R.id.nav_pokedex -> navigateToPokedex()
            R.id.nav_profile -> navigateToProfile()
            R.id.nav_settings -> navigateToSettings()
        }

        return true
    }

    private fun navigateToLocations() {
        val locationFragment = LocationsFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, locationFragment)
        fragmentTransaction.commit()
    }

    private fun navigateToItems() {
        val itemsFragment = ItemsFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, itemsFragment)
        fragmentTransaction.commit()
    }

    private fun navigateToPokedex() {

        binding.bottomNavigationView.menu.findItem(R.id.nav_pokedex).setChecked(true)

        val pokedexFragment = PokedexFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, pokedexFragment)
        fragmentTransaction.commit()
    }

    private fun navigateToProfile() {
        val profileFragment = ProfileFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, profileFragment)
        fragmentTransaction.commit()
    }

    private fun navigateToSettings() {
        val settingsFragment = SettingsFragment()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, settingsFragment)
        fragmentTransaction.commit()
    }
}