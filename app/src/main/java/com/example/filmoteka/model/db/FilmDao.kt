package com.example.filmoteka.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {
    @Upsert
    suspend fun insertToWishlist(vararg film: FilmItem)

    @Query("SELECT * FROM films_wishlist")
    fun getWishlist(): Flow<List<FilmItem>>

    @Query("DELETE FROM films_wishlist WHERE id_api=:filmId")
    suspend fun deletefromWishlist(filmId: String)
}