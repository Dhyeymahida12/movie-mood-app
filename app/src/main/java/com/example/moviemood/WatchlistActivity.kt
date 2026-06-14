package com.example.moviemood

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemood.databinding.ActivityWatchlistBinding
import kotlinx.coroutines.launch

class WatchlistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWatchlistBinding
    private lateinit var adapter: WatchlistAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        setupRecyclerView()
        observeWatchlist()
    }

    private fun setupRecyclerView() {
        adapter = WatchlistAdapter { movie ->
            lifecycleScope.launch {
                database.watchlistDao().delete(movie)
            }
        }
        binding.rvWatchlist.layoutManager = LinearLayoutManager(this)
        binding.rvWatchlist.adapter = adapter
    }

    private fun observeWatchlist() {
        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val currentUserEmail = prefs.getString("user_email", "guest") ?: "guest"

        lifecycleScope.launch {
            database.watchlistDao().getWatchlistForUser(currentUserEmail).collect { movies ->
                adapter.submitList(movies)
                binding.tvEmptyWatchlist.visibility = if (movies.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }
}
