package com.example.moviemood

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviemood.databinding.ActivityManageMoviesBinding
import kotlinx.coroutines.launch

class ManageMoviesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageMoviesBinding
    private lateinit var database: AppDatabase
    private lateinit var adapter: ManageMoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageMoviesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)
        setupRecyclerView()
        observeMovies()
    }

    private fun setupRecyclerView() {
        adapter = ManageMoviesAdapter { movie ->
            showDeleteConfirmation(movie)
        }
        binding.rvManageMovies.apply {
            layoutManager = LinearLayoutManager(this@ManageMoviesActivity)
            adapter = this@ManageMoviesActivity.adapter
        }
    }

    private fun observeMovies() {
        lifecycleScope.launch {
            database.movieDao().getAllMovies().collect { movies ->
                adapter.submitList(movies)
            }
        }
    }

    private fun showDeleteConfirmation(movie: MovieEntity) {
        AlertDialog.Builder(this)
            .setTitle("Delete Movie")
            .setMessage("Are you sure you want to delete \"${movie.title}\"?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    database.movieDao().delete(movie)
                    Toast.makeText(this@ManageMoviesActivity, "Movie deleted", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}