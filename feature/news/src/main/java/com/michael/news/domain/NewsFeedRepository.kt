package com.michael.news.domain

import com.michael.models.NewsFeedDomainModel
import kotlinx.coroutines.flow.Flow

internal interface NewsFeedRepository {
    suspend fun getNewsFeed(): Flow<List<NewsFeedDomainModel>>

    suspend fun searchNewsFeed(query: String) : Flow<List<NewsFeedDomainModel>>

}