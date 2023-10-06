package com.example.filmoteka.ui.screens.wishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.filmoteka.R
import com.example.filmoteka.model.FilmItem
import com.example.filmoteka.model.db.FilmDbItem
import com.example.filmoteka.ui.screens.home.HomeViewModel
import com.example.filmoteka.ui.screens.home.OnBottomReached
import com.example.filmoteka.ui.screens.home.TopBar
import kotlinx.coroutines.flow.Flow

@Composable
fun WishListScreen(viewModel: WishListViewModel) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Column {
        //TopBar(viewModel)
        WishlistFilmsWidget(viewModel, films = state.value.films)
    }
}

@Composable
private fun WishlistFilmsWidget(viewModel: WishListViewModel, films: List<FilmItem>) {

    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        state = gridState,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(films, { film -> film.id }) { film ->
            WishlistFilmWidget(film, viewModel)
        }
    }
    // call the extension function
//    gridState.OnBottomReached {
//        viewModel.fetchMoreItems()
//    }
}

@Composable
private fun WishlistFilmWidget(film: FilmItem, viewModel: WishListViewModel) {
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

        Row {
            IconButton(onClick = {
//                if(wishListed) {
//                    viewModel.removeFromWishlist(film.id)
//                }else{
//                    viewModel.addToWishlist(film)
//                }
            }) {
                Icon(
                    Icons.Outlined.Bookmark,
                    contentDescription = "Буду смотреть",
                    modifier = Modifier
                        .padding(5.dp)
                        .size(30.dp),
                    tint = Color.White,
                )
            }
            Icon(
                painterResource(R.drawable.icon_wishlist_add),
                contentDescription = "",
                modifier = Modifier.size(30.dp)
            )
        }

    }
}