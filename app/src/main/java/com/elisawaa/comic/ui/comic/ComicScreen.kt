package com.elisawaa.comic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.elisawaa.comic.ui.comic.ComicViewModel

@Composable
fun ComicScreen(viewModel: ComicViewModel = hiltViewModel(), id: String? = null) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(id.toString(), color = Color.White)
            state.comic?.title?.let { Text(it) }
        }
    }
}