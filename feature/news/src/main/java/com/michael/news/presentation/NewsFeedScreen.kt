package com.michael.news.presentation

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michael.base.contract.ViewEvent
import com.michael.common.displayToast
import com.michael.easylog.logInline
import com.michael.feature.news.R
import com.michael.news.domain.contract.NewsFeedSideEffect
import com.michael.news.domain.contract.NewsFeedViewAction
import com.michael.news.domain.model.NewsFeedUiModel
import com.michael.news.presentation.component.SearchBarComponent
import com.michael.news.presentation.component.ToolBarActionComponents
import com.michael.news.presentation.component.ToolBarTitleComponent
import com.michael.ui.components.BaseScreen
import com.michael.ui.components.CenteredColumn
import com.michael.ui.extensions.clickable
import com.michael.ui.extensions.collectAllEffect
import com.michael.ui.extensions.collectAsEffect
import com.michael.ui.extensions.rememberStateWithLifecycle
import com.michael.ui.theme.Dimens
import com.michael.ui.utils.boldTexStyle
import com.michael.ui.utils.mediumTexStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
object NewsFeedScreenDestination

@Composable
fun NewsFeedScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {

    val viewModel: NewsFeedViewModel = hiltViewModel()
    val state by rememberStateWithLifecycle(viewModel.state)
    var searchBarComponentVisible by remember { mutableStateOf(false) }
    val newsItems = state.searchQueryResponse.ifEmpty { state.newsFeedList }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val isFirstItemVisible by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    SubscribeToSideEffects(
        events = viewModel.events,
        context = context
    )


    LaunchedEffect(Unit) {
        viewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
    }

    BaseScreen(
        state = state,
        topAppBarContent = { ToolBarTitleComponent(text = "News Feed") },
        trailingIconOne = {
            ToolBarActionComponents(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primary,
                ),
                name = "Search",
                onClick = { searchBarComponentVisible = !searchBarComponentVisible },
                icon = Icons.Default.Search
            )
        },
        onSystemBackClick = onBackClick,
    ) {


        InfiniteListHandler(listState = listState, loadMore = {
            viewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
        })

        Column {

            AnimatedSearchBarComponent(
                searchQuery = state.searchQuery,
                searchBarComponentVisible = searchBarComponentVisible,
                onCloseSearchClick = {
                    searchBarComponentVisible = false
                },
                onValueChange = {
                    viewModel.onViewAction(NewsFeedViewAction.UpdateSearchQuery(it))
                },
                onSearch = {
                    viewModel.onViewAction(NewsFeedViewAction.SearchNewsFeed)
                }
            )

            // Handle empty state or errors
            if (newsItems.isEmpty() && !state.isLoading ) {
                CenteredColumn {
                    Text(
                        text = "No articles found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                viewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
                            },
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                val topPadding = if (isFirstItemVisible) 0.dp else 8.dp
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 8.dp, top = topPadding)
                ) {

                    items(newsItems) { article ->
                        NewsItem(article) // Custom composable for each news item
                    }
                }
            }
        }
    }
}


@Composable
fun InfiniteListHandler(
    listState: LazyListState,
    loadMore: () -> Unit
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

@Composable
fun NewsItem(article: NewsFeedUiModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)) {
            Text(text = article.title, style = boldTexStyle(size = 16))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = article.description, style = mediumTexStyle(size = 12))
        }
    }
}


@Composable
fun AnimatedSearchBarComponent(
    searchQuery: String,
    searchBarComponentVisible: Boolean = false,
    onCloseSearchClick: () -> Unit,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    AnimatedVisibility(
        visible = searchBarComponentVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)) + scaleIn(
            initialScale = 0.9f
        ),
        exit = fadeOut(animationSpec = tween(durationMillis = 300)) + scaleOut(
            targetScale = 0.9f
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SearchBarComponent(
                modifier = Modifier.weight(0.8f),
                onValueChange = onValueChange,
                searchQuery = searchQuery,
                onSearch = onSearch
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .weight(0.1f)
                    .clickable(
                        onClick = onCloseSearchClick
                    ),
                imageVector = Icons.Default.Close,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun SubscribeToSideEffects(
    events: Flow<ViewEvent>,
    context: Context
) {

    LaunchedEffect(events) {
        events.collectAllEffect <NewsFeedSideEffect> { effect ->

            logInline("Effect: $effect")

            when(effect) {
                is NewsFeedSideEffect.ShowToast -> {
                    displayToast(context, effect.message)
                }
            }
        }
    }
}