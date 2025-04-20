package com.app.abcapp.presentation.screens

import androidx.compose.runtime.Composable
import com.app.abcapp.presentation.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState = viewModel.uiState
    MainLayout(
        uiState = uiState,
        onSearch = viewModel::onSearchChanged,
        onPageChange = viewModel::onPageChanged,
        onShowStats = viewModel::toggleStats
    )
}