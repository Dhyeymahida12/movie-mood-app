package com.example.moviemood

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val email: String,
    val favoriteGenre: String = "Action",
    val memberSince: Long = System.currentTimeMillis()
)
