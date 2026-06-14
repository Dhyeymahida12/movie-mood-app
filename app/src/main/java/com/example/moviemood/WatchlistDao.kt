package com.example.moviemood

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist_movies WHERE userEmail = :email")
    fun getWatchlistForUser(email: String): Flow<List<WatchlistMovie>>

    @Query("SELECT * FROM watchlist_movies")
    fun getAllWatchlistMovies(): Flow<List<WatchlistMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: WatchlistMovie)

    @Delete
    suspend fun delete(movie: WatchlistMovie)

    @Query("SELECT EXISTS(SELECT * FROM watchlist_movies WHERE title = :title AND userEmail = :email)")
    suspend fun isMovieInWatchlist(title: String, email: String): Boolean
}
