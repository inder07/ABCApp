package com.app.abcapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.app.abcapp.R

class MainViewModel : ViewModel() {
    var uiState by mutableStateOf(
        UiState(
            images = listOf(R.drawable.image1, R.drawable.image2, R.drawable.image3),
            currentPage = 0,
            searchText = "",
            filteredItems = data[0],
            showStats = false
        )
    )
        private set

    fun onSearchChanged(query: String) {
        val currentList = data[uiState.currentPage]
        uiState = uiState.copy(
            searchText = query,
            filteredItems = currentList.filter { it.contains(query, ignoreCase = true) }
        )
    }

    fun onPageChanged(index: Int) {
        uiState = uiState.copy(
            currentPage = index,
            filteredItems = data[index],
            searchText = ""
        )
    }

    fun toggleStats() {
        uiState = uiState.copy(showStats = !uiState.showStats)
    }

    companion object {
        val data = listOf(
            listOf(
                "apple",
                "banana",
                "orange",
                "blueberry",
                "avocado",
                "item 6",
                "item 7",
                "item 8",
                "item 9"
            ),
            listOf(
                "grape",
                "peach",
                "pear",
                "kiwi",
                "melon",
                "item 6",
                "item 7",
                "item 8",
                "item 9"
            ),
            listOf(
                "strawberry",
                "cherry",
                "pineapple",
                "mango",
                "papaya",
                "item 6",
                "item 7",
                "item 8",
                "item 9"
            )
        )
    }
}