package com.example.filmoteka.ui.activities.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.filmoteka.model.db.FilmDao
import com.example.filmoteka.ui.screens.home.HomeScreen
import com.example.filmoteka.ui.screens.home.HomeViewModel
import com.example.filmoteka.ui.screens.home.TopBar
import com.example.filmoteka.ui.screens.wishlist.WishListScreen
import com.example.filmoteka.ui.screens.wishlist.WishListViewModel
import com.example.filmoteka.ui.theme.FilmotekaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FilmotekaTheme {
                val navHostController = rememberNavController()

                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(topBar = {

                }, bottomBar = {
                    NavigationBar {
                        NavigationBarItem(selected = false,
                            onClick = { navHostController.navigate("home") },
                            icon = { },
                            label = { Text(text = "Home") })

                        NavigationBarItem(selected = false,
                            onClick = { navHostController.navigate("wishlist") },
                            icon = { },
                            label = { Text(text = "Wishlist") })
                    }
                }, snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) {
                        Snackbar(snackbarData = it)
                    }
                }) { paddings ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddings),
                        navController = navHostController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            val viewModel = hiltViewModel<HomeViewModel>()

                            HomeScreen(viewModel = viewModel, snackbarHostState = snackbarHostState)
                        }

                        composable("wishlist") {
                            val viewModel = hiltViewModel<WishListViewModel>()
                            WishListScreen(viewModel = viewModel)
                        }
                    }

                }
            }
        }
    }
}