package com.example.moviemood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.moviemood.databinding.ActivityMovieDetailBinding
import kotlinx.coroutines.launch

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var database: AppDatabase

    companion object {
        const val EXTRA_MOOD = "extra_mood"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        val mood = intent.getStringExtra(EXTRA_MOOD) ?: "your mood"

        displayMovie(mood)
        setupButtons(mood)
    }

    private var currentPosterResource: Int = R.drawable.oth

    private fun displayMovie(mood: String) {
        val lowerMood = mood.lowercase()
        binding.tvMoodLabel.text = "🔍 for your research \"$mood\""

        when {
            lowerMood.contains("romance") -> {
                binding.tvMoodLabel.text = "💕  for your research \"touching romance\""
                binding.tvMovieTitle.text = "THE SUMMER I TURNED PRETTY"
                binding.tvDirectorYear.text = "Christopher Briney  -  2022"
                binding.tvRating.text = "⭐️ ⭐️ ⭐️ ⭐️ ⭐️"
                binding.tvSynopsis.text = "Belly Conklin navigates a love triangle with lifelong family friends, brothers Conrad and Jeremiah Fisher, during a transformative summer."
                currentPosterResource = R.drawable.preety_sum
            }
            lowerMood.contains("action") -> {
                binding.tvMoodLabel.text = "🎬  for your research \"epic action\""
                binding.tvMovieTitle.text = "MAD MAX: FURY ROAD"
                binding.tvDirectorYear.text = "George Miller  -  2015"
                binding.tvRating.text = "⭐️ ⭐️ ⭐️ ⭐️ ⭐️"
                binding.tvSynopsis.text = "In a post-apocalyptic wasteland, a woman rebels against a tyrannical ruler in search for her homeland with the aid of Max Rockatansky."
                currentPosterResource = R.drawable.mdmx
            }
            lowerMood.contains("comedy") -> {
                binding.tvMoodLabel.text = "😂  for your research \"comedy\""
                binding.tvMovieTitle.text = "THE HANGOVER"
                binding.tvDirectorYear.text = "Todd Phillips  -  2009"
                binding.tvRating.text = "⭐️ ⭐️ ⭐️ ⭐️"
                binding.tvSynopsis.text = "Three buddies wake up from a bachelor party in Las Vegas with no memory of the previous night and the bachelor missing."
                currentPosterResource = R.drawable.images_14
            }
            lowerMood.contains("thriller") -> {
                binding.tvMoodLabel.text = "🔥  for your research \"thriller\""
                binding.tvMovieTitle.text = "INCEPTION"
                binding.tvDirectorYear.text = "Christopher Nolan  -  2010"
                binding.tvRating.text = "⭐️ ⭐️ ⭐️ ⭐️ ⭐️"
                binding.tvSynopsis.text = "A thief who steals corporate secrets through dream-sharing technology is given the inverse task of planting an idea into a CEO's mind."
                currentPosterResource = R.drawable.inceptionn
            }
            lowerMood.contains("horror") -> {
                binding.tvMoodLabel.text = "👻  for your research \"horror\""
                binding.tvMovieTitle.text = "THE CONJURING"
                binding.tvDirectorYear.text = "James Wan  -  2013"
                binding.tvRating.text = "⭐️ ⭐️ ⭐️ ⭐️ ⭐️"
                binding.tvSynopsis.text = "Paranormal investigators Ed and Lorraine Warren work to help a family terrorized by a dark presence in their farmhouse."
                currentPosterResource = R.drawable.hrr
            }
            lowerMood.contains("sci-fi") || lowerMood.contains("scifi") -> {
                binding.tvMoodLabel.text = "🚀  for your research \"sci-fi\""
                binding.tvMovieTitle.text = "INTERSTELLAR"
                binding.tvDirectorYear.text = "Christopher Nolan  -  2014"
                binding.tvRating.text = "⭐️ ⭐️ ⭐️ ⭐️ ⭐️"
                binding.tvSynopsis.text = "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival."
                currentPosterResource = R.drawable.interst
            }
            lowerMood.contains("drama") -> {
                binding.tvMoodLabel.text = "🎭  for your research \"drama\""
                binding.tvMovieTitle.text = "THE WHALE"
                binding.tvDirectorYear.text = "Darren Aronofsky  -  2022"
                binding.tvRating.text = "⭐️ ⭐️ ⭐️ ⭐️"
                binding.tvSynopsis.text = "A reclusive English teacher attempts to reconnect with his estranged teenage daughter."
                currentPosterResource = R.drawable._1bnm4d97wl__sl500_
            }
            lowerMood.contains("classic") -> {
                binding.tvMoodLabel.text = "🌟  for your research \"classic\""
                binding.tvMovieTitle.text = "THE GODFATHER"
                binding.tvDirectorYear.text = "Francis Ford Coppola  -  1972"
                binding.tvRating.text = "⭐️ ⭐️ ⭐️ ⭐️ ⭐️"
                binding.tvSynopsis.text = "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."
                currentPosterResource = R.drawable.the_godfather__the_game
            }
            else -> {
                binding.tvMovieTitle.text = "FEATURED MOVIE"
                binding.tvDirectorYear.text = "Recommended for you"
                binding.tvSynopsis.text = "We found some great movies matching your mood: $mood. Explore more to find your perfect match!"
                currentPosterResource = R.drawable.oth
            }
        }
        binding.ivMoviePoster.setImageResource(currentPosterResource)
    }

    private fun setupButtons(mood: String) {
        binding.btnAddToWatchlist.setOnClickListener {
            val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val currentUserEmail = prefs.getString("user_email", "guest") ?: "guest"

            val movie = WatchlistMovie(
                userEmail = currentUserEmail,
                title = binding.tvMovieTitle.text.toString(),
                directorYear = binding.tvDirectorYear.text.toString(),
                synopsis = binding.tvSynopsis.text.toString(),
                posterResource = currentPosterResource
            )

            lifecycleScope.launch {
                database.watchlistDao().insert(movie)
                binding.btnAddToWatchlist.text = "✓ Added to watchlist!"
                Toast.makeText(this@MovieDetailActivity, "Saved to your watchlist!", Toast.LENGTH_SHORT).show()
                
                // Navigate to Watchlist page
                val intent = Intent(this@MovieDetailActivity, WatchlistActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnViewMore.setOnClickListener {
            val intent = Intent(this, MovieListActivity::class.java)
            intent.putExtra(MovieListActivity.EXTRA_MOOD, mood)
            startActivity(intent)
        }

        binding.tvSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
