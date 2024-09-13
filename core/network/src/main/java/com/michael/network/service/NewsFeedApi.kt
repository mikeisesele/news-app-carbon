package com.michael.network.service

import com.michael.network.model.NewsFeedResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsFeedApi {

    @GET("latest")
    suspend fun getNewsFeed(
        @Query("language") language: String = "en",
        @Query("q") searchParam: String? = null,
        @Query("page") nextPage: String? = null,
    ): NewsFeedResponse

//    @GET("latest")
//    suspend fun searchNewsFeed(
//        @Query("language") language: String = "en",
//        @Query("q") searchParam: String,
//    ): NewsFeedResponse
//
//    @GET("latest")
//    suspend fun getMoreNewsFeed(
//        @Query("language") language: String = "en",
//        @Query("page") nextPage: String,
//    ): NewsFeedResponse

}