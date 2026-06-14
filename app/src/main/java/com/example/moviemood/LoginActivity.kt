package com.example.moviemood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moviemood.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    // ViewBinding connects to activity_login.xml
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Connect this Kotlin file to activity_login.xml
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSignUpButton()
    }

    private fun setupSignUpButton() {
        binding.btnSignUp.setOnClickListener {

            // Read what the user typed in each box
            val email    = binding.etEmail.text?.toString()?.trim() ?: ""
            val password = binding.etPassword.text?.toString() ?: ""
            val confirm  = binding.etConfirmPassword.text?.toString() ?: ""

            // Admin Login Check
            if (email == "admin@moviemood.com" && password == "admin123") {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
                finish()
                return@setOnClickListener
            }

            // Check each field one by one
            when {
                email.isEmpty() -> {
                    Toast.makeText(
                        this,
                        "Please enter your email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !email.contains("@") -> {
                    Toast.makeText(
                        this,
                        "Please enter a valid email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                password.isEmpty() -> {
                    Toast.makeText(
                        this,
                        "Please enter a password!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                password.length < 6 -> {
                    Toast.makeText(
                        this,
                        "Password must be at least 6 characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                confirm.isEmpty() -> {
                    Toast.makeText(
                        this,
                        "Please confirm your password!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                password != confirm -> {
                    Toast.makeText(
                        this,
                        "Passwords do not match!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    // All checks passed — Save user info to DB and SharedPreferences
                    val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    prefs.edit().putString("user_email", email).apply()

                    // Save User to Database
                    val user = UserEntity(email = email)
                    lifecycleScope.launch(Dispatchers.IO) {
                        AppDatabase.getDatabase(applicationContext).userDao().insertUser(user)
                    }

                    Toast.makeText(
                        this,
                        "Welcome to MovieMood!",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this, HomeActivity::class.java)

                    // Clear the back stack so user can't go back to login
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }
    }
}