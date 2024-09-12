package com.michael.di

import com.michael.network.service.NewsFeedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseHttpClient(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addNetworkInterceptor(loggingInterceptor)
        builder.addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val requestWithApiKey: Request = originalRequest.newBuilder()
                .addHeader("X-ACCESS-KEY", APIKEY)
                .build()
            chain.proceed(requestWithApiKey)
        }
        return builder
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient.Builder,
        converterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .client(okHttpClient.build())
            .addConverterFactory(converterFactory)
            .build()
    }


    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NewsFeedApi {
        return retrofit.create(NewsFeedApi::class.java)
    }
}

private const val NETWORK_TIMEOUT = 30L
private const val BASEURL = "https://newsdata.io/api/1/"
private const val APIKEY = "pub_5324732474707aa61624d6f83145cf364f772"
