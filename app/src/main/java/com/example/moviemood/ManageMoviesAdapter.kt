package com.example.moviemood

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviemood.databinding.ItemManageMovieBinding

class ManageMoviesAdapter(private val onDeleteClick: (MovieEntity) -> Unit) :
    ListAdapter<MovieEntity, ManageMoviesAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(val binding: ItemManageMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemManageMovieBinding.inflate(
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
            tvCategory.text = movie.category
            
            if (!movie.imageUri.isNullOrEmpty()) {
                Glide.with(ivPoster.context)
                    .load(movie.imageUri)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(movie.posterRes)
                    .into(ivPoster)
            } else {
                ivPoster.setImageResource(movie.posterRes)
            }

            btnDelete.setOnClickListener { onDeleteClick(movie) }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
        override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
            return oldItem == newItem
        }
    }
}
