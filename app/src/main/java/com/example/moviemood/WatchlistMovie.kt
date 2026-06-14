package com.example.moviemood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist_movies")
data class WatchlistMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userEmail: String, // Link to UserEntity
    val title: String,
    val directorYear: String,
    val synopsis: String,
    val posterResource: Int
)
