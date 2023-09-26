package com.example.filmoteka.ui.screens.wishlist

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmoteka.ui.screens.home.HomeFilm
import com.example.filmoteka.ui.screens.home.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(WishListState(1))
    val state: StateFlow<WishListState> = _state

    init{
        viewModelScope.launch {

        }
    }
}