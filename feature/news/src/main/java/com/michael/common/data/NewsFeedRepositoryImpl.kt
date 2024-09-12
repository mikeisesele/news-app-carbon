package com.michael.common.data

import com.michael.base.providers.DispatcherProvider
import com.michael.localdata.dao.NewsDao
import com.michael.models.NewsFeedDomainModel
import com.michael.network.service.NewsFeedApi
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.mappers.toEntity
import com.michael.ui.extensions.asEmittedFlow
import com.michael.ui.extensions.safeReturnableOperation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: NewsFeedApi,
    private val newsFeedDao: NewsDao,
    private val dispatcherProvider: DispatcherProvider,
) : NewsFeedRepository {

    override suspend fun getNewsFeed(): Flow<List<NewsFeedDomainModel>> = flow {

        val newsList = safeReturnableOperation(
            operation = {
                val news = newsFeedDao.getNews()
                news.ifEmpty {
                    val apiResponse = apiService.getNewsFeed()
                    val domainModel = apiResponse.toEntity()
                    newsFeedDao.insertNews(domainModel)
                    domainModel
                }
            },
            actionOnException = { throw it!! },
            exceptionMessage = "getNewsFeed error"
        )

        emit(newsList ?: emptyList())

    }.flowOn(dispatcherProvider.io)

    override suspend fun searchNewsFeed(query: String): Flow<List<NewsFeedDomainModel>> = flow {

        val newsList = safeReturnableOperation(
            operation = {
                val news = newsFeedDao.searchNews(query)
                news.ifEmpty {
                    apiService.getNewsFeed(searchParam = query).toEntity()
                }
            },
            actionOnException = { throw it!! },
            exceptionMessage = "searchNewsFeed error"
        )

        emit(newsList ?: emptyList())

    }.flowOn(dispatcherProvider.io)

    override suspend fun getNewsDetail(id: Int): Flow<NewsFeedDomainModel> = flow {

        emit(
            newsFeedDao
                .getNewsDetail(id)
        )

    }.flowOn(dispatcherProvider.io)


}