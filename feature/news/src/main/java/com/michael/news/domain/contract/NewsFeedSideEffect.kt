package com.michael.news.domain.contract

import com.michael.base.contract.SideEffect
import com.michael.base.model.MessageState

internal interface NewsFeedSideEffect : SideEffect {
    data class ShowErrorMessage(val errorMessageState: MessageState) : NewsFeedSideEffect
}