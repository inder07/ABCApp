package com.app.abcapp.presentation.viewmodel

data class UiState(
    val images: List<Int> = emptyList(),
    val currentPage: Int = 0,
    val searchText: String = "",
    val filteredItems: List<String> = emptyList(),
    val showStats: Boolean = false
)