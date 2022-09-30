package com.elisawaa.comic

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.elisawaa.comic.ui.favorites.FavoriteViewModel


@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    Row {
        Text(" Favorites", color = MaterialTheme.colorScheme.onBackground)
    }
}
