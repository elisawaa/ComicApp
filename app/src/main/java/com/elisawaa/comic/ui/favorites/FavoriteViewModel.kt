package com.elisawaa.comic.ui.favorites


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elisawaa.comic.data.ComicRepository
import com.elisawaa.comic.data.model.Comic
import com.elisawaa.comic.data.model.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ComicRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ComicUIState(loading = true))
    val uiState: StateFlow<ComicUIState> = _uiState

    fun observeFavorites() {
        viewModelScope.launch {
            repository.fetchFavorites()
                .catch { e ->
                    _uiState.value = ComicUIState(error = e.message)
                }
                .collect { favorites ->
                    when (favorites) {
                        is ResponseState.Error -> _uiState.value =
                            ComicUIState(error = favorites.t.message)
                        ResponseState.Loading -> _uiState.value = ComicUIState(loading = true)
                        is ResponseState.Success -> _uiState.value =
                            ComicUIState(favorites = favorites.data)
                    }
                }
        }
    }

    fun updateFavorite(comic: Comic) {
        val newComic = comic.copy(favorited = !comic.favorited)

        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(newComic)
        }
        _uiState.value = _uiState.value.copy(favorites = _uiState.value.favorites?.map {
            if (comic.id == it.id) {
                newComic
            } else {
                it
            }
        })
    }
}

data class ComicUIState(
    val favorites: List<Comic>? = null,
    val loading: Boolean = false,
    val error: String? = null
)