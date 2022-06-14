package com.eliseubatista.pocketdex.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eliseubatista.pocketdex.R
import com.eliseubatista.pocketdex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        supportActionBar?.hide()

        setupClickListeners(binding)
    }

    private fun setupClickListeners(binding: ActivityMainBinding) {
        binding.titleScreenTouchImage.setOnClickListener {
            openControlActivity()
        }
    }

    private fun openControlActivity() {
        val intent = Intent(this, ControlActivity::class.java)
        startActivity(intent)
    }
}