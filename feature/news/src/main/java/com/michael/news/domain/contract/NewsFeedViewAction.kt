package com.michael.news.domain.contract

import com.michael.base.contract.BaseViewAction

internal interface NewsFeedViewAction : BaseViewAction {
    data object GetNewsFeed: NewsFeedViewAction
    data class UpdateSearchQuery(val query: String): NewsFeedViewAction
    data object SearchNewsFeed: NewsFeedViewAction
}