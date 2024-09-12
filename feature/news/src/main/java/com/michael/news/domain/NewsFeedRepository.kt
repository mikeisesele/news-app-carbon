package com.michael.news.domain

import com.michael.models.NewsFeedDomainModel
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {
    suspend fun getNewsFeed(): Flow<List<com.michael.models.NewsFeedDomainModel>>

}