package com.michael.news.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.michael.base.contract.BaseViewModel
import com.michael.base.model.MessageState
import com.michael.base.providers.DispatcherProvider
import com.michael.common.emptyImmutableList
import com.michael.common.toImmutableList
import com.michael.easylog.logInline
import com.michael.news.data.NewsFeedRepositoryImpl
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.contract.NewsFeedState
import com.michael.news.domain.contract.NewsFeedViewAction
import com.michael.news.domain.mappers.toUiModel
import com.michael.news.domain.model.NewsFeedDomainModel
import com.michael.ui.extensions.collectBy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    dispatcherProvider: DispatcherProvider,
    private val newsFeedRepository: NewsFeedRepositoryImpl
) : BaseViewModel<NewsFeedState, NewsFeedViewAction>(
    NewsFeedState.initialState,
    dispatcherProvider
) {

    init {
        getNewsFeed()
    }

    override fun onViewAction(viewAction: NewsFeedViewAction) {
        when(viewAction) {
            NewsFeedViewAction.GetNewsFeed -> getNewsFeed()
        }
    }

    private fun getNewsFeed() {

        logInline("VIEW MDOEL")
       //  if (currentState.isLoading && currentState.isLastPage) return // Avoid loading if already loading or if it's the last page

        launch {
            newsFeedRepository.getNewsFeed().collectBy(
                onStart = ::onLoading,
                onEach = ::processNewsFeedResponse,
                onError = ::onError
            )
        }
    }

    private fun onLoading () {
        updateState { state ->
            state.copy(
                isLoading = true,
                errorState = null
            )
        }
    }

    private fun onError (error: Throwable) {
        updateState { state ->
            state.copy(
                isLoading = false,
                errorState = MessageState.Inline(error.message.orEmpty())
            )
        }
    }

    private fun processNewsFeedResponse(newsFeed: List<NewsFeedDomainModel>) {
        "Processing".logInline()
        if (newsFeed.isEmpty() && currentState.newsFeedList.isNotEmpty()) {
            updateState { state ->
                state.copy(
                    isLoading = false,
                )
            }
        } else {
           val newNewsFeed = currentState.newsFeedList.toMutableList() + newsFeed.toUiModel()


            updateState { state ->
                state.copy(
                    isLoading = false,
                    errorState = null,
                    newsFeedList = newNewsFeed.toImmutableList().logInline("List")
                )
            }
        }
    }


}