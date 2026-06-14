package com.example.moviemood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemood.databinding.ActivityMovieListBinding
import kotlinx.coroutines.launch

class MovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieListBinding
    private lateinit var database: AppDatabase
    private lateinit var movieAdapter: MovieAdapter
    private var allMovies: List<Movie> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        
        val mood = intent.getStringExtra(EXTRA_MOOD) ?: "Action"
        binding.tvMoodLabel.text = "Discovering $mood Vibes"

        setupRecyclerView()
        setupListeners()
        loadMoviesByMood(mood)
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(emptyList()) { movie ->
            saveToWatchlist(movie)
        }
        binding.rvMovies.layoutManager = LinearLayoutManager(this)
        binding.rvMovies.adapter = movieAdapter
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener { finish() }
        
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Real-time Search
        binding.etSearch.addTextChangedListener { text ->
            filterAndSort(text.toString())
        }

        // Sorting Chips
        binding.chipGroupSort.setOnCheckedStateChangeListener { _, _ ->
            filterAndSort(binding.etSearch.text.toString())
        }
    }

    private fun loadMoviesByMood(mood: String) {
        val lowerMood = mood.lowercase()
        lifecycleScope.launch {
            val dbCategory = when {
                lowerMood.contains("horror") -> "Horror"
                lowerMood.contains("action") -> "Action"
                lowerMood.contains("comedy") -> "Comedy"
                lowerMood.contains("romance") -> "Romance"
                lowerMood.contains("sci-fi") || lowerMood.contains("scifi") -> "Sci-Fi"
                lowerMood.contains("thriller") -> "Thriller"
                lowerMood.contains("drama") -> "Drama"
                lowerMood.contains("classic") -> "Classic"
                lowerMood.contains("web series") -> "Web Series"
                else -> "Action"
            }

            allMovies = database.movieDao().getMoviesByCategory(dbCategory).map { entity ->
                Movie(
                    title = entity.title,
                    directorYear = entity.directorYear,
                    synopsis = entity.synopsis,
                    posterRes = entity.posterRes,
                    rating = entity.rating,
                    imageUri = entity.imageUri
                )
            }
            filterAndSort(binding.etSearch.text.toString())
        }
    }

    private fun filterAndSort(query: String) {
        var filtered = if (query.isEmpty()) {
            allMovies
        } else {
            allMovies.filter { it.title.contains(query, ignoreCase = true) || it.synopsis.contains(query, ignoreCase = true) }
        }

        // Apply Sorting
        filtered = when (binding.chipGroupSort.checkedChipId) {
            R.id.chipRating -> filtered.sortedByDescending { it.rating.filter { char -> char.isDigit() || char == '.' }.toDoubleOrNull() ?: 0.0 }
            R.id.chipTitle -> filtered.sortedBy { it.title }
            R.id.chipNewest -> filtered.reversed()
            else -> filtered
        }

        movieAdapter.updateData(filtered)
        
        // Show empty state if no results
        binding.emptyState.visibility = if (filtered.isEmpty()) View.VISIBLE else View.GONE
        binding.rvMovies.visibility = if (filtered.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun saveToWatchlist(movie: Movie) {
        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val currentUserEmail = prefs.getString("user_email", "guest") ?: "guest"

        val watchlistMovie = WatchlistMovie(
            userEmail = currentUserEmail,
            title = movie.title,
            directorYear = movie.directorYear,
            synopsis = movie.synopsis,
            posterResource = movie.posterRes
        )
        lifecycleScope.launch {
            database.watchlistDao().insert(watchlistMovie)
            Toast.makeText(this@MovieListActivity, "Saved to Watchlist!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val EXTRA_MOOD = "extra_mood"
    }
}
