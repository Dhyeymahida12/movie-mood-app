package com.example.moviemood

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviemood.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    // ViewBinding connects to activity_settings.xml
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this Kotlin file to activity_settings.xml
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSwitches()
        setupCheckboxes()
    }

    private fun setupSwitches() {

        // Clear stored data switch
        binding.switchClearData.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    this,
                    "Stored data will be cleared!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Dark mode switch — already ON by default
        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    this,
                    "Dark mode enabled!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Dark mode disabled!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupCheckboxes() {

        // Keep history checkbox
        binding.cbKeepHistory.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    this,
                    "History will be saved!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Store passwords checkbox
        binding.cbStorePasswords.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(
                    this,
                    "Passwords will be stored!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}