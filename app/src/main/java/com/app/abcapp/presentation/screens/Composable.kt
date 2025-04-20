package com.app.abcapp.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.app.abcapp.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselSection(images: List<Int>, pagerState: PagerState) {
    androidx.compose.foundation.pager.HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Image(
            painter = painterResource(id = images[page]),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(dimensionResource(R.dimen.carousel_height))
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_large),
                    top = dimensionResource(R.dimen.padding_large),
                    end = dimensionResource(R.dimen.padding_large),
                    bottom = dimensionResource(R.dimen.padding_small)
                )
                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius_large)))
        )
    }
}

@Composable
fun DotsIndicator(currentPage: Int, totalDots: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { index ->
            val color =
                if (currentPage == index) MaterialTheme.colorScheme.secondary else Color.Gray
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .size(8.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color)
            )
        }
    }
}

@Composable
fun SearchBar(searchText: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.search), color = Color.Black) },
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Black) },
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
    )
}

@Composable
fun ListItem(item: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 3.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f))
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image3),
            contentDescription = null,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                item.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                stringResource(R.string.list_item_subtitle),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsBottomSheet(currentPage: Int, filteredList: List<String>, onDismiss: () -> Unit) {
    val charCounts = filteredList.joinToString("").groupingBy { it }.eachCount()
        .toList().sortedByDescending { it.second }.take(3)

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Text(
            text = stringResource(R.string.list_stats, currentPage + 1, filteredList.size),
            modifier = Modifier.padding(16.dp)
        )
        charCounts.forEach {
            Text(
                text = "${it.first} = ${it.second}",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SyncPagerWithList(
    images: List<Int>,
    onPageChanged: (Int) -> Unit
): PagerState {
    val pagerState = rememberPagerState(pageCount = { images.size })

    LaunchedEffect(pagerState.settledPage) {
        onPageChanged(pagerState.settledPage)
    }

    return pagerState
}
