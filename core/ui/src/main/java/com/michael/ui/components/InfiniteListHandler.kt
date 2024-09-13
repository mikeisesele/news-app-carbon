package com.michael.ui.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

@Composable
fun InfiniteListHandler(
    listState: LazyListState, loadMore: () -> Unit
) {
    // Check if the user is near the bottom of the list to trigger loading more
    val shouldLoadMore = remember {
        derivedStateOf {
            val visibleItemsInfo = listState.layoutInfo.visibleItemsInfo
            if (visibleItemsInfo.isNotEmpty()) {
                val lastVisibleItemIndex = visibleItemsInfo.last().index
                val totalItemCount = listState.layoutInfo.totalItemsCount
                lastVisibleItemIndex >= totalItemCount - 5 // Trigger loading when 5 items remain
            } else {
                false
            }
        }
    }
    LaunchedEffect(shouldLoadMore.value) {
        if (shouldLoadMore.value) {
            loadMore()
        }
    }
}