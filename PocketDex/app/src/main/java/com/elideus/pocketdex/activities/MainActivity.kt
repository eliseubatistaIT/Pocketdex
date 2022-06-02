package com.elideus.pocketdex.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.elideus.pocketdex.R
import com.elideus.pocketdex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        supportActionBar?.hide()

        setupClickListeners(binding)
    }

    override fun onStart() {
        super.onStart()
    }

    private fun setupClickListeners(binding: ActivityMainBinding) {
        binding.titleScreenTouchImage.setOnClickListener { view: View ->
            openControlActivity()
        }
    }

    private fun openControlActivity() {
        val intent = Intent(this, ControlActivity::class.java)
        startActivity(intent)
    }
}