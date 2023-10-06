package com.example.filmoteka.ui.screens.wishlist

import com.example.filmoteka.model.FilmItem
import com.example.filmoteka.model.db.FilmDbItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class WishListState (
    val films: List<FilmItem> = emptyList(),
)