package com.elisawaa.comic.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.List
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.elisawaa.comic.R

object ComicRoute {
    const val LIST = "List"
    const val COMIC = "Comic"
    const val FAVORITES = "Favorites"
}

data class ComicTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

class ComicNavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: ComicTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    ComicTopLevelDestination(
        route = ComicRoute.LIST,
        selectedIcon = Icons.Default.List,
        unselectedIcon = Icons.Outlined.List,
        iconTextId = R.string.comic_list
    ),
    ComicTopLevelDestination(
        route = ComicRoute.COMIC,
        selectedIcon = Icons.Default.Image,
        unselectedIcon = Icons.Outlined.Image,
        iconTextId = R.string.comic
    ),
    ComicTopLevelDestination(
        route = ComicRoute.FAVORITES,
        selectedIcon = Icons.Default.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder,
        iconTextId = R.string.favorites
    )
)