package com.example.filmoteka.ui.screens.home

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.filmoteka.R

@Composable
fun HomeScreen(viewModel: HomeViewModel, snackbarHostState: SnackbarHostState) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.error.collect {
            snackbarHostState.showSnackbar(it.message?.ifEmpty { null } ?: "Unknown error")
        }
    }

    HomeFilmsWidget(films = state.value.films)
}

@Composable
private fun HomeFilmsWidget(films: List<HomeFilm>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(films, { film -> film.id }) { film ->
            HomeFilmWidget(film)
        }
    }
}

@Composable
private fun HomeFilmWidget(film: HomeFilm) {
    val placeholderPhoto = painterResource(id = R.drawable.placeholder)
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.667f),
            model = film.poster,
            contentDescription = null,
            placeholder = placeholderPhoto,
            error = placeholderPhoto,
        )
        Text(text = film.title, style = MaterialTheme.typography.titleLarge)
        Text(text = film.year.toString(), style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun HomeFilmsWidgetPreview() {
    HomeFilmsWidget(
        listOf(
            HomeFilm("1", Uri.EMPTY, "Название 1", 1990),
            HomeFilm("2", Uri.EMPTY, "Название 2", 2000),
            HomeFilm("3", Uri.EMPTY, "Название 3", 2010)
        )
    )
}

@Preview(widthDp = 320)
@Composable
private fun HomeFilmWidgetPreview() {
    HomeFilmWidget(film = HomeFilm("1", Uri.EMPTY, "Название 1", 1990))
}