package com.example.moviemood

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnticipateOvershootInterpolator
import com.example.moviemood.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupMoodChips()
        setupDiscoverButton()
        setupIcons()
        startEntranceAnimations()
    }

    private fun startEntranceAnimations() {
        val views = listOf(
            binding.tvAppTitle, binding.tvSubtitle, binding.tvChooseMood,
            binding.btnActionMood, binding.btnComedyMood,
            binding.btnThrillerMood, binding.btnRomanceMood,
            binding.btnHorrorMood, binding.btnSciFiMood,
            binding.btnDramaMood, binding.btnClassicMood,
            binding.cardMoodInput, binding.btnDiscover
        )

        views.forEachIndexed { index, view ->
            view.alpha = 0f
            view.translationY = 80f
            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay(150L + (index * 40L))
                .setDuration(700)
                .setInterpolator(AnticipateOvershootInterpolator(1.0f))
                .start()
        }
    }

    private fun setupMoodChips() {
        val chips = listOf(
            binding.btnActionMood   to "Epic Action",
            binding.btnComedyMood   to "Comedy",
            binding.btnThrillerMood to "Thriller",
            binding.btnRomanceMood  to "Romance",
            binding.btnHorrorMood   to "Horror",
            binding.btnSciFiMood    to "Sci-Fi",
            binding.btnDramaMood    to "Drama",
            binding.btnClassicMood  to "Classic"
        )

        chips.forEach { (chip, mood) ->
            chip.setOnClickListener {
                val intent = Intent(this, LoadingActivity::class.java)
                intent.putExtra(MovieDetailActivity.EXTRA_MOOD, mood)
                startActivity(intent)
            }
        }
    }

    private fun setupDiscoverButton() {
        binding.btnDiscover.setOnClickListener {
            val typedMood = binding.etMoodInput.text?.toString()?.trim()

            if (typedMood.isNullOrEmpty()) {
                Toast.makeText(this, "Please describe your mood first!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, LoadingActivity::class.java)
            intent.putExtra(MovieDetailActivity.EXTRA_MOOD, typedMood)
            startActivity(intent)
        }
    }

    private fun setupIcons() {
        binding.tvSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        
        binding.tvWatchlist.setOnClickListener {
            startActivity(Intent(this, WatchlistActivity::class.java))
        }

        binding.tvProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
