package com.elisawaa.comic.ui.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elisawaa.comic.R
import com.elisawaa.comic.data.model.Comic
import com.elisawaa.comic.ui.EmptyScreen
import com.elisawaa.comic.ui.ErrorScreen
import com.elisawaa.comic.ui.LoadingScreen


@Composable
fun FavoriteScreen(viewModel: FavoriteViewModel = hiltViewModel(), navigateToComic: (Int) -> Unit) {
    val state by viewModel.uiState.collectAsState()

    if (state.loading) {
        LoadingScreen()
    }

    if (state.error != null) {
        ErrorScreen(state.error)
    }

    state.favorites?.let { favorites ->
        FavoriteBody(favorites, viewModel, navigateToComic)
    }

    if (!state.loading && state.error == null && state.favorites.isNullOrEmpty()) {
        EmptyScreen()
    }
}

@Composable
fun FavoriteBody(
    favorites: List<Comic>,
    viewModel: FavoriteViewModel,
    navigateToComic: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(favorites) { favorite ->
            FavoriteItem(comic = favorite, viewModel) { navigateToComic(favorite.id) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteItem(comic: Comic, viewModel: FavoriteViewModel, onClick: () -> Unit) {
    val icon = if (comic.favorited) Icons.Outlined.FavoriteBorder else Icons.Default.Favorite
    Card(
        onClick = { onClick() }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(128.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(comic.img)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_baseline_image_24),
                contentDescription = comic.alt,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(112.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    comic.title,
                    color = MaterialTheme.colorScheme.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2
                )
                Text(
                    comic.alt,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            IconButton(
                onClick = { viewModel.updateFavorite(comic) },
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Icon(icon, null)
            }

        }
    }
}
