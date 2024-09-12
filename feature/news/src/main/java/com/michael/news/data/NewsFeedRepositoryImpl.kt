package com.michael.news.data

import com.michael.base.providers.DispatcherProvider
import com.michael.easylog.logInline
import com.michael.network.service.NewsFeedApi
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.mappers.toEntity
import com.michael.news.domain.model.NewsFeedDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: NewsFeedApi,
    private val dispatcherProvider: DispatcherProvider,
) : NewsFeedRepository {
    private var nextPage: String? = null

    override suspend fun getNewsFeed(): Flow<List<NewsFeedDomainModel>> = flow {
        try {
            // Fetch news feed from the API
            val apiResponse = apiService.getNewsFeed()
            nextPage = apiResponse.nextPage
            emit(apiResponse.toEntity())
        }catch (e: Exception) {
            e.logInline("getNewsFeed error")
            e.printStackTrace()
            throw e
        }

    }.flowOn(dispatcherProvider.io)

}