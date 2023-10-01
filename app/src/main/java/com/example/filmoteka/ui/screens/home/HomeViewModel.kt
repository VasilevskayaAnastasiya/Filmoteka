package com.example.filmoteka.ui.screens.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmoteka.model.data.ErrorDTO
import com.example.filmoteka.model.data.FilmModelDTO
import com.example.filmoteka.repo.KinopoiskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val retrofit: Retrofit, val kinopoiskRepo: KinopoiskRepo
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _error = Channel<Throwable>(Channel.BUFFERED)
    val error: Flow<Throwable> = _error.consumeAsFlow()

    init {
//        loadFilms(1)
    }

    fun fetchMoreItems() {
        loadFilms(_state.value.lastPage + 1, _state.value.search)
    }

    fun loadFilms(page: Int, search: String) {
        viewModelScope.launch {
            val result: Response<FilmModelDTO>
            if (search != "") {
                result = kinopoiskRepo.searchByNameFilms(search, page)
            } else {
                result = kinopoiskRepo.getFilms(page)
            }

            if (result.isSuccessful) {
                val films = result.body()?.docs?.map { item ->
                    HomeFilm(id = item.id.toString(),
                        title = item.name,
                        year = item.year,
                        poster = item.poster?.url?.let { Uri.parse(it) })
                } ?: emptyList()
                _state.update { oldState ->
                    oldState.copy(
                        films = oldState.films + films,
                        lastPage = page,
                        search = oldState.search
                    )
                }
            } else {
                val err = ErrorDTO.parseError(retrofit, result)
                _error.send(RuntimeException(err?.message ?: "Internal error"))
            }
        }
    }

    fun searchByNameFilms(search: String){
        _state.update { oldState ->
            oldState.copy(
                films = emptyList(),
                lastPage = 0,
                search = search
            )
        }
        loadFilms(0, search)
    }
}