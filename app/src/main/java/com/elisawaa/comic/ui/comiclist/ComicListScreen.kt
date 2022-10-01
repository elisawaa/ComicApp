package com.elisawaa.comic

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elisawaa.comic.data.model.Comic
import com.elisawaa.comic.ui.EmptyScreen
import com.elisawaa.comic.ui.ErrorScreen
import com.elisawaa.comic.ui.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicListScreen(
    viewModel: ComicListViewModel = hiltViewModel(), navigateToComic: (Int) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    Column {
        TopAppBar({ Text("Comics") })

        SearchBar(
            state.searchQuery ?: "", modifier = Modifier.padding(16.dp)
        ) { viewModel.updateSearchQuery(it) }

        if (state.loading) {
            LoadingScreen()
        }

        if (state.error != null) {
            ErrorScreen(state.error)
        }

        if (state.filteredComics.isNotEmpty()) {
            ComicListBody(
                state.filteredComics, navigateToComic
            )
        }

        if (!state.loading && state.error == null && state.filteredComics.isEmpty()) {
            EmptyScreen()
        }
    }
}

@Composable
fun ComicListBody(
    comics: List<Comic>, navigateToComic: (Int) -> Unit
) {
    val orientation = LocalConfiguration.current.orientation
    val columns = if (orientation == ORIENTATION_PORTRAIT) 2 else 3

    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns), modifier = Modifier.padding(top = 16.dp)
        ) {
            items(comics) { comic ->
                ComicListItem(comic) { navigateToComic(comic.id) }
            }
        }
    }
}

@Composable
fun ComicListItem(
    comic: Comic, onClick: () -> Unit
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(end = 8.dp, bottom = 8.dp)
        .clickable { onClick() }) {
        ComicListImage(imgUrl = comic.img, contentDescription = comic.alt)
        Text(
            text = "#${comic.id} - ${comic.title}",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(2.dp),
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
fun ComicListImage(imgUrl: String, contentDescription: String) {
    AsyncImage(model = ImageRequest.Builder(LocalContext.current).data(imgUrl).crossfade(true)
        .build(),
        placeholder = painterResource(R.drawable.ic_baseline_image_24),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .aspectRatio(1f)
            .fillMaxSize()
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.DarkGray),
                    startY = size.height / 4,
                    endY = size.height
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.Multiply)
                }
            })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(query: String, modifier: Modifier = Modifier, onQueryChange: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            if (query != "") {
                IconButton(onClick = {
                    onQueryChange("")
                }) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp)
    )
}
