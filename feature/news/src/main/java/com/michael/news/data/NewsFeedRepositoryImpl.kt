package com.michael.news.data

import com.michael.base.providers.DispatcherProvider
import com.michael.easylog.logInline
import com.michael.easylog.logInlineNullable
import com.michael.localdata.dao.NewsDao
import com.michael.network.service.NewsFeedApi
import com.michael.news.domain.NewsFeedRepository
import com.michael.news.domain.mappers.toEntity
import com.michael.models.NewsFeedDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: NewsFeedApi,
    private val newsFeedDao: NewsDao,
    private val dispatcherProvider: DispatcherProvider,
) : NewsFeedRepository {

    override suspend fun getNewsFeed(): Flow<List<NewsFeedDomainModel>> = flow {
        try {

            val news = newsFeedDao.getNews()

            if (news.isNotEmpty()) {
                news.logInline()
                emit(news)
            } else {

                // Fetch news feed from the API
                val apiResponse = apiService.getNewsFeed()
                val domainModel = apiResponse.toEntity()
                newsFeedDao.insertNews(domainModel)
                domainModel.logInlineNullable()
                emit(domainModel)
            }
        } catch (e: Exception) {
            e.logInline("getNewsFeed error")
            e.printStackTrace()
            throw e
        }

    }.flowOn(dispatcherProvider.io)

}