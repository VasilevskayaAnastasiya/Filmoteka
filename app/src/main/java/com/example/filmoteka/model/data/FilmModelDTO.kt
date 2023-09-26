package com.example.filmoteka.model.data

data class FilmModelDTO(
    val docs: List<Doc>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)