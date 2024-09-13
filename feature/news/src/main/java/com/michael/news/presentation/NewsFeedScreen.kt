package com.michael.news.presentation

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michael.base.contract.ViewEvent
import com.michael.base.model.MessageState
import com.michael.common.Ignored
import com.michael.common.displayToast
import com.michael.common.randomFrom
import com.michael.feature.news.R
import com.michael.news.domain.contract.NewsFeedSideEffect
import com.michael.news.domain.contract.NewsFeedViewAction
import com.michael.news.presentation.component.AnimatedSearchBarComponent
import com.michael.news.presentation.component.NewsItem
import com.michael.news.presentation.component.ToolBarActionComponents
import com.michael.newsdetail.presentation.components.CenteredText
import com.michael.ui.components.BaseScreen
import com.michael.ui.components.CenteredColumn
import com.michael.ui.components.InfiniteListHandler
import com.michael.ui.components.LoadingAnimation
import com.michael.ui.components.ToolBarTitleComponent
import com.michael.ui.extensions.collectAllEffect
import com.michael.ui.extensions.rememberStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

@Serializable
object NewsFeedScreenDestination

@Composable
fun NewsFeedScreen(modifier: Modifier = Modifier, onNewsCardClick: (Int) -> Unit) {

    val viewModel: NewsFeedViewModel = hiltViewModel()
    val state by rememberStateWithLifecycle(viewModel.state)
    var searchBarComponentVisible by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val isFirstItemVisible by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val errorMessage = state.errorState as? MessageState.Inline


    val activity = context as? Activity

    SubscribeToSideEffects(
        events = viewModel.events, context = context
    )

    LaunchedEffect(Unit) {
        viewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
    }

    BaseScreen(
        state = state,
        topAppBarContent = { ToolBarTitleComponent(text = stringResource(R.string.news_feed)) },
        trailingIconOne = {
            ToolBarActionComponents(
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.primary,
                ),
                name = stringResource(R.string.search),
                onClick = { searchBarComponentVisible = !searchBarComponentVisible },
                icon = Icons.Default.Search
            )
        },
        onSystemBackClick = {
            if (searchBarComponentVisible) {
                searchBarComponentVisible = false
            } else {
                activity?.finishAffinity()
            }
        },
    ) {

        Column {

            AnimatedSearchBarComponent(searchQuery = state.searchQuery,
                searchBarComponentVisible = searchBarComponentVisible,
                onCloseSearchClick = {
                    searchBarComponentVisible = false
                },
                onValueChange = {
                    viewModel.onViewAction(NewsFeedViewAction.UpdateSearchQuery(it))
                },
                onSearch = {
                    searchBarComponentVisible = false
                    viewModel.onViewAction(NewsFeedViewAction.SearchNewsFeed)
                })

            // Handle empty state or errors
            if (state.newsFeedList.isEmpty() && !state.isLoading) {
                CenteredText(
                    buttonText = stringResource(R.string.retry),
                    text = errorMessage?.message.orEmpty()
                )
            } else if (
                state.searchQueryResponse.isEmpty() &&
                !state.isLoading &&
                state.searchQuery.isNotEmpty()
            ) {
                CenteredText(
                    buttonText = stringResource(R.string.reload_news),
                    text = stringResource(
                        R.string.no_result_matches_your_search_query,
                        state.searchQuery
                    ),
                    onCenteredTextAction = {
                        searchBarComponentVisible = false
                        viewModel.onViewAction(NewsFeedViewAction.UpdateSearchQuery(""))
                        viewModel.onViewAction(NewsFeedViewAction.GetNewsFeed)
                    })
            } else {
                InfiniteListHandler(listState = listState, loadMore = {
                    viewModel.onViewAction(NewsFeedViewAction.LoadMoreNews)
                })

                val topPadding = if (isFirstItemVisible) 0.dp else 8.dp
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(
                        start = 16.dp, end = 16.dp, bottom = 8.dp, top = topPadding
                    )
                ) {
                    items(state.newsFeedList, key = { it.id.randomFrom() }) { article ->
                        NewsItem(
                            article = article, onNewsCardClick = onNewsCardClick
                        ) // Custom composable for each news item
                    }
                    if (state.isLoadingMore) {
                        item {
                            CenteredColumn(
                                modifier = Modifier.height(100.dp)
                            ) {
                                LoadingAnimation(
                                    circleSize = 10.dp,
                                    travelDistance = 20.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun SubscribeToSideEffects(
    events: Flow<ViewEvent>, context: Context
) {
    LaunchedEffect(events) {
        events.collectAllEffect<NewsFeedSideEffect> { effect ->
            when (effect) {
                is NewsFeedSideEffect.ShowErrorMessage -> {
                    when (effect.errorMessageState) {
                        is MessageState.Toast -> {
                            displayToast(context, effect.errorMessageState.message)
                        }

                        else -> Ignored
                    }
                }
            }
        }
    }
}