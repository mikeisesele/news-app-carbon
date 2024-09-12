package com.michael.news.domain

import com.michael.news.domain.model.NewsFeedDomainModel
import kotlinx.coroutines.flow.Flow

interface NewsFeedRepository {
    suspend fun getNewsFeed(): Flow<List<NewsFeedDomainModel>>

}