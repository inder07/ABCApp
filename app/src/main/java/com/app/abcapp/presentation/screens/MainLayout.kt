package com.app.abcapp.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.app.abcapp.R
import com.app.abcapp.presentation.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainLayout(
    uiState: UiState,
    onSearch: (String) -> Unit,
    onPageChange: (Int) -> Unit,
    onShowStats: () -> Unit
) {
    val pagerState = SyncPagerWithList(images = uiState.images, onPageChanged = onPageChange)
    val listState = rememberLazyListState()
    val sheetState = rememberModalBottomSheetState()

    Box(Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.padding_large))
        ) {
            item {
                CarouselSection(images = uiState.images, pagerState = pagerState)
                DotsIndicator(pagerState.currentPage, uiState.images.size)
            }
            stickyHeader {
                SearchBar(searchText = uiState.searchText, onValueChange = onSearch)
            }
            items(uiState.filteredItems) {
                ListItem(item = it)
            }
        }

        FloatingActionButton(
            onClick = onShowStats,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Text(stringResource(R.string.fab_icon))
        }

        if (uiState.showStats) {
            StatsBottomSheet(
                currentPage = uiState.currentPage,
                filteredList = uiState.filteredItems,
                onDismiss = onShowStats
            )
        }
    }
}