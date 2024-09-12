package com.michael.news.presentation

import androidx.lifecycle.viewModelScope
import com.michael.base.contract.BaseViewModel
import com.michael.base.contract.ViewEvent
import com.michael.base.model.MessageState
import com.michael.base.providers.DispatcherProvider
import com.michael.common.toImmutableList
import com.michael.easylog.ifNullSetDefault
import com.michael.easylog.logInline
import com.michael.news.data.NewsFeedRepositoryImpl
import com.michael.news.domain.contract.NewsFeedState
import com.michael.news.domain.contract.NewsFeedViewAction
import com.michael.news.domain.mappers.toUiModel
import com.michael.models.NewsFeedDomainModel
import com.michael.news.domain.contract.NewsFeedSideEffect
import com.michael.ui.extensions.collectBy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val newsFeedRepository: NewsFeedRepositoryImpl
) : BaseViewModel<NewsFeedState, NewsFeedViewAction>(
    NewsFeedState.initialState,
    dispatcherProvider
) {
    override fun onViewAction(viewAction: NewsFeedViewAction) {
        when (viewAction) {
            NewsFeedViewAction.GetNewsFeed -> getNewsFeed()
            is NewsFeedViewAction.UpdateSearchQuery -> updateSearchQuery(viewAction.query)
            is NewsFeedViewAction.SearchNewsFeed -> searchNewsFeed()
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



    private fun onLoading() {
        updateState { state ->
            state.copy(
                isLoading = true,
                errorState = null
            )
        }
    }

    private fun onError(error: Throwable) {

        val errorMessage = error.message.ifNullSetDefault { error.localizedMessage }

        dispatchViewEvent(ViewEvent.Effect(NewsFeedSideEffect.ShowToast(errorMessage)))

        updateState { state ->
            state.copy(
                isLoading = false,
                errorState = MessageState.Inline(error.message.orEmpty())
            )
        }
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

}