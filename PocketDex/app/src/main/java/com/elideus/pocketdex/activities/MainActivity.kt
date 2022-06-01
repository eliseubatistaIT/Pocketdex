package com.elideus.pocketdex.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.elideus.pocketdex.R
import com.elideus.pocketdex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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