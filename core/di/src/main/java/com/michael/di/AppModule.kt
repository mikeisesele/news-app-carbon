package com.michael.di

import android.app.Application
import android.content.Context
import com.michael.base.providers.StringProvider
import com.michael.common.StringProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApplicationContext(application: Application): Context =
        application.applicationContext

    @Provides
    fun providesResourceProvider(resourceProvider: StringProviderImpl): StringProvider =
        resourceProvider

    @Provides
    fun provideErrorHandler(errorHandler: com.michael.common.ErrorHandler): com.michael.base.providers.ErrorHandler =
        errorHandler
}
