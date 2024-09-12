package com.michael.newsdetail.domain.contract

import com.michael.base.contract.BaseState
import com.michael.base.model.MessageState
import com.michael.newsdetail.presentation.model.NewsDetailUiModel

internal data class NewsDetailState(
    override val isLoading: Boolean,
    override val errorState: MessageState?,
    val newsDetail: NewsDetailUiModel?
) : BaseState {

    companion object {
        val initialState = NewsDetailState(
            isLoading = false,
            errorState = null,
            newsDetail = null
        )
    }
}