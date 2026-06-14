package com.example.moviemood

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviemood.databinding.ItemMovieCardBinding

data class Movie(
    val title: String,
    val directorYear: String,
    val synopsis: String,
    val posterRes: Int = R.drawable.ic_launcher_background,
    val rating: String = "⭐️ ⭐️ ⭐️ ⭐️ ⭐️",
    val imageUri: String? = null
)

class MovieAdapter(
    private var movies: List<Movie>,
    private val onAddClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(private val binding: ItemMovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(movie: Movie) {
            binding.tvMovieTitle.text = movie.title
            binding.tvDirectorYear.text = movie.directorYear
            binding.tvSynopsis.text = movie.synopsis
            binding.tvRating.text = movie.rating
            
            if (movie.imageUri != null) {
                Glide.with(binding.ivMoviePoster.context)
                    .load(Uri.parse(movie.imageUri))
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.ivMoviePoster)
            } else {
                binding.ivMoviePoster.setImageResource(movie.posterRes)
            }
            
            binding.btnAddToWatchlist.setOnClickListener {
                onAddClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    fun updateData(newMovies: List<Movie>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}
