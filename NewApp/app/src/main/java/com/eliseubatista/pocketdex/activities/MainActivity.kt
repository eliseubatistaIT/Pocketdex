package com.eliseubatista.pocketdex.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.controls.Control
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.ActivityMainBinding
import com.google.android.material.bottomappbar.BottomAppBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        setupClickListeners(binding)
    }

    private fun setupClickListeners(binding: ActivityMainBinding) {
        binding.titleScreenTouchImage.setOnClickListener { view: View ->
            openControlActivity(view)
        }
    }

    private fun openControlActivity(view: View) {
        val intent = Intent(this, ControlActivity::class.java)
        startActivity(intent)
    }
}