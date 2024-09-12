package com.michael.common.di

import com.michael.base.providers.DispatcherProvider
import com.michael.localdata.dao.NewsDao
import com.michael.network.service.NewsFeedApi
import com.michael.common.data.NewsFeedRepositoryImpl
import com.michael.network.provider.NetworkStateProvider
import com.michael.news.domain.NewsFeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsFeedRepository(
        apiService: NewsFeedApi,
        newsDao: NewsDao,
        dispatcherProvider: DispatcherProvider,
        networkStateProvider: NetworkStateProvider
    ): NewsFeedRepository {
        return NewsFeedRepositoryImpl(apiService, newsDao, dispatcherProvider, networkStateProvider)
    }
}

