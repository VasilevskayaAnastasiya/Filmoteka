package com.example.filmoteka.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films_wishlist")
data class FilmItem(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "id_api") val id_api: String
)
