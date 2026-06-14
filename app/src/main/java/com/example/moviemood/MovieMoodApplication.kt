package com.example.moviemood

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class MovieMoodApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Apply saved theme preference on app start
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDarkMode = prefs.getBoolean("dark_mode", false)

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}