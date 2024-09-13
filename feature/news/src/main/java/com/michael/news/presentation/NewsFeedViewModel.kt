package com.michael.news.presentation

import com.michael.base.contract.BaseViewModel
import com.michael.base.contract.ViewEvent
import com.michael.base.model.MessageState
import com.michael.base.providers.DispatcherProvider
import com.michael.base.providers.StringProvider
import com.michael.common.cleanMessage
import com.michael.common.emptyImmutableList
import com.michael.common.toImmutableList
import com.michael.easylog.ifNullSetDefault
import com.michael.easylog.logInline
import com.michael.easylog.logInlineNullable
import com.michael.feature.news.R
import com.michael.models.NewsFeedDomainModel
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.contract.NewsFeedSideEffect
import com.michael.news.domain.contract.NewsFeedState
import com.michael.news.domain.contract.NewsFeedViewAction
import com.michael.news.domain.mappers.toUiModel
import com.michael.ui.extensions.collectBy
import com.michael.ui.extensions.singleFlowOnItemReceivedInScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
internal class NewsFeedViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val newsFeedRepository: NewsFeedRepository,
    private val stringProvider: StringProvider
) : BaseViewModel<NewsFeedState, NewsFeedViewAction>(
    NewsFeedState.initialState,
    dispatcherProvider
) {
    override fun onViewAction(viewAction: NewsFeedViewAction) {
        when (viewAction) {
            NewsFeedViewAction.GetNewsFeed -> getNewsFeed()
            is NewsFeedViewAction.UpdateSearchQuery -> updateSearchQuery(viewAction.query)
            is NewsFeedViewAction.SearchNewsFeed -> searchNewsFeed()
            is NewsFeedViewAction.LoadMoreNews -> loadMoreNews()
        }
    }

    private fun getNewsFeed() {
        launch {
            newsFeedRepository.getNewsFeed().collectBy(
                onStart = ::onLoading,
                onEach = ::processNewsFeedResponse,
                onError = ::onError
            )
        }
    }

    private fun updateSearchQuery(query: String) {
        updateState { state ->
            state.copy(
                searchQuery = query
            )
        }
    }

    private fun searchNewsFeed() {
        launch {
            newsFeedRepository.searchNewsFeed(currentState.searchQuery).collectBy(
                onStart = ::onLoading,
                onEach = ::processSearchQueryResponse,
                onError = ::onError
            )
        }
    }

    private fun loadMoreNews() {
        launch {
            newsFeedRepository.loadMoreNews().singleFlowOnItemReceivedInScope(
                onStart = ::onMoreLoading,
                onItemReceived = ::processPaginatedNewsFeedResponse,
                onError = ::onError,
                coroutineScope = this
            )
        }
    }


    private fun onLoading() {
        updateState { state ->
            state.copy(
                isLoading = true,
                errorState = null,
            )
        }
    }
    private fun onMoreLoading() {
            updateState { state ->
                state.copy(
                    isLoadingMore = true,
                )
            }
        }

    private fun onError(error: Throwable) {
        val errorMessage = error.message.ifNullSetDefault {
            error.localizedMessage.ifEmpty {
                stringProvider.getString(
                    R.string.something_went_wrong
                )
            }
        }.orEmpty()

        updateState { state ->
            state.copy(
                isLoading = false,
                isLoadingMore = false,
                errorState = MessageState.Inline(errorMessage.cleanMessage())
            )
        }
        dispatchViewEvent(
            ViewEvent.Effect(
                NewsFeedSideEffect.ShowErrorMessage(
                    errorMessageState = MessageState.Inline(errorMessage)
                )
            )
        )
    }

    private fun processNewsFeedResponse(newsFeed: List<NewsFeedDomainModel>) {
        updateState { state ->
            state.copy(
                isLoading = false,
                errorState = null,
                newsFeedList = newsFeed.toUiModel().toImmutableList()
            )
        }

    }

    private fun processSearchQueryResponse(newsFeed: List<NewsFeedDomainModel>) {

        updateState { state ->
            state.copy(
                isLoading = false,
                errorState = null,
                searchQueryResponse = newsFeed.toUiModel().toImmutableList()
            )
        }
    }

    private fun processPaginatedNewsFeedResponse(newsFeed: List<NewsFeedDomainModel>) {
        val updatedList = (currentState.newsFeedList + newsFeed.toUiModel().toImmutableList()).toImmutableList()
        updateState { state ->
            state.copy(
                isLoadingMore = false,
                newsFeedList = updatedList
            )
        }
    }
}