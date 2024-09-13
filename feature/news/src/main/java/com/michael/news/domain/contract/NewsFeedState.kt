package com.michael.news.domain.contract

import com.michael.base.contract.BaseState
import com.michael.base.model.MessageState
import com.michael.common.ImmutableList
import com.michael.common.emptyImmutableList
import com.michael.news.presentation.model.NewsFeedUiModel

internal data class NewsFeedState(
    override val isLoading: Boolean,
    override val errorState: MessageState?,
    val searchQuery: String,
    val isLoadingMore: Boolean,
    val newsFeedList: ImmutableList<NewsFeedUiModel>,
    val searchQueryResponse: ImmutableList<NewsFeedUiModel>,
) : BaseState {
    companion object {
        val initialState = NewsFeedState(
            isLoading = false,
            isLoadingMore = false,
            errorState = null,
            newsFeedList = emptyImmutableList(),
            searchQueryResponse = emptyImmutableList(),
            searchQuery = ""
        )
    }
}

