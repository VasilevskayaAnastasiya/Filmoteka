package com.example.filmoteka.model

import android.net.Uri

data class FilmItem(
    val id: String,
    val poster: Uri?,
    val title: String,
    val year: Int
)

