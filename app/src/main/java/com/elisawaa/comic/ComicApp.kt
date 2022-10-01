package com.elisawaa.comic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elisawaa.comic.ui.favorites.FavoriteScreen
import com.elisawaa.comic.ui.navigation.ComicBottomNavigationBar
import com.elisawaa.comic.ui.navigation.ComicNavigationActions
import com.elisawaa.comic.ui.navigation.ComicRoute
import com.elisawaa.comic.ui.navigation.ComicTopLevelDestination


@Composable
fun ComicApp() {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        ComicNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: ComicRoute.COMIC

    ComicAppContent(
        navController = navController,
        selectedDestination = selectedDestination,
        navigateToTopLevelDestination = navigationActions::navigateTo
    )
}


@Composable
fun ComicAppContent(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (ComicTopLevelDestination) -> Unit
) {
    Row(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)

        ) {
            ComicNavHost(
                navController = navController,
                modifier = Modifier.weight(1f),
            )

            ComicBottomNavigationBar(
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigateToTopLevelDestination
            )

        }
    }
}

@Composable
private fun ComicNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ComicRoute.COMIC,
    ) {
        composable(ComicRoute.LIST) {
            ComicListScreen() { comicId ->
                navigateToComic(navController, comicId)
            }
        }
        composable(ComicRoute.COMIC) {
            ComicScreen()
        }
        composable(ComicRoute.COMIC_SPECIFIC) {
            ComicScreen()
        }
        composable(ComicRoute.FAVORITES) {
            FavoriteScreen() { comicId -> navigateToComic(navController, comicId) }
        }
    }
}

fun navigateToComic(navController: NavController, comicId: Int) {
    navController.navigate(
        ComicRoute.COMIC_SPECIFIC.replace(
            "{id}",
            comicId.toString()
        )
    )
}