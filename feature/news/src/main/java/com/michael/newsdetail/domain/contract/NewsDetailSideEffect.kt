package com.michael.newsdetail.domain.contract

import com.michael.base.contract.SideEffect
import com.michael.base.model.MessageState
import com.michael.news.domain.contract.NewsFeedSideEffect

interface NewsDetailSideEffect : SideEffect {
    data class ShowErrorMessage(val errorMessageState: MessageState) : NewsFeedSideEffect
}