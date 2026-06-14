package com.example.moviemood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemood.databinding.ItemWatchlistMovieBinding

class WatchlistAdapter(private val onDeleteClick: (WatchlistMovie) -> Unit) :
    ListAdapter<WatchlistMovie, WatchlistAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(val binding: ItemWatchlistMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWatchlistMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.apply {
            tvTitle.text = movie.title
            tvDirectorYear.text = movie.directorYear
            tvSynopsis.text = movie.synopsis
            ivPoster.setImageResource(movie.posterResource)
            btnDelete.setOnClickListener { onDeleteClick(movie) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<WatchlistMovie>() {
        override fun areItemsTheSame(oldItem: WatchlistMovie, newItem: WatchlistMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WatchlistMovie, newItem: WatchlistMovie): Boolean {
            return oldItem == newItem
        }
    }
}
