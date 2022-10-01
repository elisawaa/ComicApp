package com.elisawaa.comic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elisawaa.comic.data.model.Comic
import com.elisawaa.comic.ui.EmptyScreen
import com.elisawaa.comic.ui.ErrorScreen
import com.elisawaa.comic.ui.LoadingScreen
import com.elisawaa.comic.ui.comic.ComicViewModel
import com.elisawaa.comic.ui.comic.ZoomableImage


@Composable
fun ComicScreen(viewModel: ComicViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()

    if (state.loading) {
        LoadingScreen()
    }

    if (state.error != null) {
        ErrorScreen(state.error)
    }

    state.comic?.let { comic ->
        ComicBody(comic)
    }

    if (!state.loading && state.error == null && state.comic == null) {
        EmptyScreen()
    }

}

@Composable
fun ComicBody(comic: Comic) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    "#${comic.id} - ${comic.title}",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    "Published: ${comic.day}/${comic.month}/${comic.year}",
                    color = MaterialTheme.colorScheme.onBackground
                )
                ComicImage(comic.img, comic.alt, scrollState)

                Text(
                    "Transcript",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(comic.transcript, color = MaterialTheme.colorScheme.onBackground)
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Random comic",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            FloatingActionButton(
                onClick = {
                    // TODO EWB
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 24.dp, bottom = 16.dp)
            ) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = null)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComicImage(imgUrl: String, contentDescription: String, scrollState: ScrollState) {
    ZoomableImage(
        imgUrl = imgUrl,
        contentDescription = contentDescription,
        contentScale = ContentScale.FillWidth,
        scrollState = scrollState,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxSize()
    )
}