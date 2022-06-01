package com.eliseubatista.pocketdex.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.ActivityControlBinding

class ControlActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityControlBinding>(this, R.layout.activity_control)

        /*
        val navView: BottomNavigationView = binding.navView

        val navController = this.findNavController(R.id.nav_host_fragment_control)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_locations,
                R.id.navigation_items,
                R.id.navigation_pokedex,
                R.id.navigation_profile,
                R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        */

    }
}