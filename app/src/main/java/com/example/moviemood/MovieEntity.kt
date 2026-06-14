package com.example.moviemood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val directorYear: String,
    val synopsis: String,
    val rating: String,
    val imageUri: String? = null,
    val posterRes: Int = R.drawable.ic_launcher_background,
    val category: String
)
