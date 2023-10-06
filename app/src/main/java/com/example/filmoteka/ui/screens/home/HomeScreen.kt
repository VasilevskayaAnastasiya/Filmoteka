package com.example.filmoteka.ui.screens.home

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
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.filmoteka.R
import com.example.filmoteka.model.FilmItem

@Composable
fun HomeScreen(viewModel: HomeViewModel, snackbarHostState: SnackbarHostState) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.error.collect {
            snackbarHostState.showSnackbar(it.message?.ifEmpty { null } ?: "Unknown error")
        }
    }

    Column {
        TopBar(viewModel)
        HomeFilmsWidget(viewModel, films = state.value.films, wishlist = state.value.wishlist)
    }
}

@Composable
private fun HomeFilmsWidget(viewModel: HomeViewModel, films: List<FilmItem>, wishlist: Set<String>) {

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
            HomeFilmWidget(film, viewModel, wishlist)
        }
    }
    // call the extension function
    gridState.OnBottomReached {
        viewModel.fetchMoreItems()
    }
}

@Composable
private fun HomeFilmWidget(film: FilmItem, viewModel: HomeViewModel, wishlist: Set<String>) {
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

        val wishListed = wishlist.contains(film.id)
        val iconWishlist = if (wishListed) {
            Icons.Outlined.Bookmark
        } else {
            Icons.Outlined.BookmarkAdd
        }
        Row {
            IconButton(onClick = {
                if(wishListed) {
                    viewModel.removeFromWishlist(film.id)
                }else{
                    viewModel.addToWishlist(film)
                }
            }) {
                Icon(
                    iconWishlist,
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

@Composable
fun LazyGridState.OnBottomReached(
    buffer: Int = 4, onLoadMore: () -> Unit
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val layoutInfo = layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - buffer)
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }.collect { onLoadMore() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: HomeViewModel) {
    Column {
        SearchViewWidget(remember { mutableStateOf(TextFieldValue("")) }, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchViewWidget(state: MutableState<TextFieldValue>, viewModel: HomeViewModel) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
            viewModel.searchByNameFilms(value.text.toString())
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(onClick = {
                    state.value = TextFieldValue("")
                    viewModel.searchByNameFilms("")
                }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            //leadingIconColor = Color.White,
            //trailingIconColor = Color.White,
            //backgroundColor = colorResource(id = R.color.teal_200),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


//@Preview
//@Composable
//private fun HomeFilmsWidgetPreview() {
//    HomeFilmsWidget(
//        listOf(
//            HomeFilm("1", Uri.EMPTY, "Название 1", 1990),
//            HomeFilm("2", Uri.EMPTY, "Название 2", 2000),
//            HomeFilm("3", Uri.EMPTY, "Название 3", 2010)
//        )
//    )
//}
//
//@Preview(widthDp = 320)
//@Composable
//private fun HomeFilmWidgetPreview() {
//    HomeFilmWidget(film = HomeFilm("1", Uri.EMPTY, "Название 1", 1990))
//}