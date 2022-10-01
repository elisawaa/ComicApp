package com.elisawaa.comic.ui.comic


import androidx.lifecycle.SavedStateHandle
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
import kotlin.random.Random


@HiltViewModel
class ComicViewModel @Inject constructor(
    private val repository: ComicRepository,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ComicUIState(loading = true))
    val uiState: StateFlow<ComicUIState> = _uiState

    private val comicId: String? = savedStateHandle["id"]

    init {
        if (comicId != null) {
            getComic(comicId.toInt())
        } else {
            getRandomComic()
        }
    }

    fun getComic(comicId: Int) {
        viewModelScope.launch {
            repository.fetchComic(comicId)
                .catch { e ->
                    _uiState.value = ComicUIState(error = e.message)
                }
                .collect { comic ->
                    when (comic) {
                        is ResponseState.Error -> _uiState.value =
                            ComicUIState(error = comic.t.message)
                        ResponseState.Loading -> _uiState.value = ComicUIState(loading = true)
                        is ResponseState.Success -> _uiState.value =
                            ComicUIState(comic = comic.data)
                    }
                }
        }
    }

    fun updateFavorite(comic: Comic) {
        val newComic = comic.copy(favorited = !comic.favorited)
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(newComic)
        }
        _uiState.value = _uiState.value.copy(comic = newComic)
    }

    fun getRandomComic() {
        viewModelScope.launch {
            repository.fetchRecentComic().collect { response ->
                if (response is ResponseState.Success) {
                    val maxId = response.data.id
                    val randomId = Random.nextInt(1, maxId)
                    getComic(randomId)
                }
            }
        }
    }
}

data class ComicUIState(
    val comic: Comic? = null,
    val loading: Boolean = false,
    val error: String? = null
)