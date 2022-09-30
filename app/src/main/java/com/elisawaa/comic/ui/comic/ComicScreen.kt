package com.elisawaa.comic

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elisawaa.comic.ui.comic.ComicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicScreen(viewModel: ComicViewModel = hiltViewModel(), id: String? = null) {
    val state by viewModel.uiState.collectAsState()
    state.comic?.let { comic ->
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    comic.title,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    "Published: ${comic.day}/${comic.month}/${comic.year}",
                    color = MaterialTheme.colorScheme.onBackground
                )
                ComicImage(comic.img, comic.alt)

                Text(
                    "Transcript",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(comic.transcript, color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
fun ComicImage(imgUrl: String, contentDescription: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_baseline_image_24),
        contentDescription = contentDescription,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
    )
}