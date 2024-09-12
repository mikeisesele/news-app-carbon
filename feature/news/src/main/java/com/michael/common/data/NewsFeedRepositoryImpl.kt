package com.michael.common.data

import com.michael.base.providers.DispatcherProvider
import com.michael.common.Constants.GET_NEWS_ERROR_LOG
import com.michael.common.Constants.NO_INTERNET
import com.michael.common.Constants.SEARCH_NEWS_ERROR_LOG
import com.michael.easylog.logInline
import com.michael.localdata.dao.NewsDao
import com.michael.models.NewsFeedDomainModel
import com.michael.network.provider.NetworkStateProvider
import com.michael.network.service.NewsFeedApi
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.mappers.toEntity
import com.michael.ui.extensions.safeReturnableOperation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: NewsFeedApi,
    private val newsFeedDao: NewsDao,
    private val dispatcherProvider: DispatcherProvider,
    private val networkStateProvider: NetworkStateProvider
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
            actionOnException = {
                val exception = if (!networkStateProvider.isConnected) {
                    Exception(NO_INTERNET)
                } else {
                    it!!
                }
                throw exception
            },
            exceptionMessage = GET_NEWS_ERROR_LOG
        )

        emit(newsList ?: emptyList())

    }.flowOn(dispatcherProvider.io)

    override suspend fun searchNewsFeed(query: String): Flow<List<NewsFeedDomainModel>> = flow {

        val newsList = safeReturnableOperation(
            operation = {
                val searchDataResult = newsFeedDao.searchNews(query).ifEmpty {
                    apiService.searchNewsFeed(searchParam = query).toEntity()
                }
                searchDataResult

            },
            actionOnException = {
                throw if (!networkStateProvider.isConnected) {
                    Exception(NO_INTERNET)
                } else {
                    it!!
                }
            },
            exceptionMessage = SEARCH_NEWS_ERROR_LOG
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
