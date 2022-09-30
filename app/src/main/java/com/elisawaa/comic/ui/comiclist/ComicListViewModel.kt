package com.elisawaa.comic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elisawaa.comic.data.ComicRepository
import com.elisawaa.comic.data.model.Comic
import com.elisawaa.comic.data.model.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ComicListViewModel @Inject constructor(private val repository: ComicRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ComicUIState(loading = true))
    val uiState: StateFlow<ComicUIState> = _uiState

    init {
        observeComic()
    }

    private fun observeComic() {
        viewModelScope.launch {
            repository.fetchAllComics()
                .catch { e ->
                    _uiState.value = ComicUIState(error = e.message)
                }
                .collect { comic ->
                    when (comic) {
                        is ResponseState.Error -> _uiState.value =
                            ComicUIState(error = comic.t.message)
                        ResponseState.Loading -> _uiState.value = ComicUIState(loading = true)
                        is ResponseState.Success -> _uiState.value =
                            ComicUIState(comics = comic.data)
                    }
                }
        }
    }
}

data class ComicUIState(
    val comics: List<Comic> = emptyList(),
    val selectedComic: Comic? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)