package com.elideus.pocketdex.activities

import android.os.Bundle
import android.view.Menu
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.elideus.pocketdex.R
import com.elideus.pocketdex.databinding.ActivityControlBinding

class ControlActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityControlBinding>(this, R.layout.activity_control)

        setupActionBar(binding)

        setupBottomNavBar(binding)
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.pokedex_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    private fun setupActionBar(binding: ActivityControlBinding) {
        toolbar = binding.toolbar
        toolbar.title = "Pokédex"

        setSupportActionBar(toolbar)
    }

    private fun setupBottomNavBar(binding: ActivityControlBinding) {
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_location,
                R.id.navigation_items,
                R.id.navigation_pokedex,
                R.id.navigation_profile,
                R.id.navigation_settings
            )
        )

        if (supportActionBar != null) {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.pocketdex_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp()
    }
}