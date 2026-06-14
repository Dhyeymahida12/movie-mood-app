package com.example.moviemood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.moviemood.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val prefs by lazy { getSharedPreferences("settings", Context.MODE_PRIVATE) }
    private val userPrefs by lazy { getSharedPreferences("user_prefs", Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize state based on saved preferences
        val isDarkMode = prefs.getBoolean("dark_mode", false)
        binding.switchDarkMode.isChecked = isDarkMode
        
        binding.cbKeepHistory.isChecked = prefs.getBoolean("keep_history", true)
        binding.cbStorePasswords.isChecked = prefs.getBoolean("store_passwords", false)

        // Pre-fill the update email field with current value
        val currentEmail = userPrefs.getString("user_email", "")
        binding.etUpdateEmail.setText(currentEmail)

        setupSwitches()
        setupCheckboxes()
        setupProfileUpdate()
        setupDevInfo()
        setupLogout()
    }

    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Logout") { _, _ ->
                    // Clear user session data
                    userPrefs.edit().clear().apply()
                    
                    // Navigate back to LoginActivity and clear back stack
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun setupDevInfo() {
        binding.btnShowDevInfo.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("App Information")
                .setMessage("Developer: Dhyey Mahida\n\nMovieMood helps you discover the perfect movie based on your current mood using advanced mood analysis.")
                .setPositiveButton("Close", null)
                .show()
        }
    }

    private fun setupProfileUpdate() {
        binding.btnSaveProfile.setOnClickListener {
            val newEmail = binding.etUpdateEmail.text.toString().trim()
            if (newEmail.isNotEmpty()) {
                userPrefs.edit().putString("user_email", newEmail).apply()
                Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                // Hide keyboard or clear focus if desired
                binding.etUpdateEmail.clearFocus()
            } else {
                Toast.makeText(this, "Please enter a valid name or email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupSwitches() {
        binding.switchClearData.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                userPrefs.edit().clear().apply()
                binding.etUpdateEmail.setText("")
                Toast.makeText(this, "User data cleared!", Toast.LENGTH_SHORT).show()
                // Reset switch after clearing
                binding.switchClearData.postDelayed({
                    binding.switchClearData.isChecked = false
                }, 500)
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            // Save preference
            prefs.edit().putBoolean("dark_mode", isChecked).apply()

            // Apply theme immediately
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupCheckboxes() {
        binding.cbKeepHistory.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("keep_history", isChecked).apply()
        }

        binding.cbStorePasswords.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("store_passwords", isChecked).apply()
        }
        
        binding.rowClearSavedData.setOnClickListener {
             userPrefs.edit().clear().apply()
             binding.etUpdateEmail.setText("")
             Toast.makeText(this, "All saved data cleared!", Toast.LENGTH_SHORT).show()
        }
    }
}