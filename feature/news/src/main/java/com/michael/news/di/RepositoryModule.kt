package com.michael.news.di

import com.michael.base.providers.DispatcherProvider
import com.michael.network.service.NewsFeedApi
import com.michael.news.data.NewsFeedRepositoryImpl
import com.michael.news.domain.NewsFeedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideNewsFeedRepository(
        apiService: NewsFeedApi,
        dispatcherProvider: DispatcherProvider
    ): NewsFeedRepository {
        return NewsFeedRepositoryImpl(apiService, dispatcherProvider)
    }
}

