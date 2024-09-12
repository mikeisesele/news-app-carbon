package com.michael.news.data

import com.michael.base.providers.DispatcherProvider
import com.michael.easylog.logInline
import com.michael.localdata.dao.NewsDao
import com.michael.models.NewsFeedDomainModel
import com.michael.network.service.NewsFeedApi
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.mappers.toEntity
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
        try {

            val news = newsFeedDao.getNews().logInline()
            if (news.isNotEmpty()) {
                emit(news)
            } else {
                val apiResponse = apiService.getNewsFeed()
                val domainModel = apiResponse.toEntity()
                newsFeedDao.insertNews(domainModel)
                emit(domainModel)
            }
        } catch (e: Exception) {
            e.logInline("getNewsFeed error")
            e.printStackTrace()
            throw e
        }

    }.flowOn(dispatcherProvider.io)

    override suspend fun searchNewsFeed(query: String): Flow<List<NewsFeedDomainModel>> = flow {
        val news = newsFeedDao.searchNews(query)
        try {
        emit(
            news.ifEmpty {
                apiService.getNewsFeed(searchParam = query).toEntity()
            }
        )
        } catch (e: Exception) {
            e.logInline("getNewsFeed error")
            e.printStackTrace()
            throw e
        }
    }.flowOn(dispatcherProvider.io)

}