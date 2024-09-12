package com.michael.news.domain.contract

import com.michael.base.contract.SideEffect

interface NewsFeedSideEffect : SideEffect {
    data class ShowToast(val message: String) : NewsFeedSideEffect
}