package com.michael.news.domain.contract

import com.michael.base.contract.SideEffect

internal interface NewsFeedSideEffect : SideEffect {
    data class ShowToast(val message: String) : NewsFeedSideEffect
}