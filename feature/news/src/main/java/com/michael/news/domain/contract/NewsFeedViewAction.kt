package com.michael.news.domain.contract

import com.michael.base.contract.BaseViewAction

interface NewsFeedViewAction : BaseViewAction {
    data object GetNewsFeed: NewsFeedViewAction
}