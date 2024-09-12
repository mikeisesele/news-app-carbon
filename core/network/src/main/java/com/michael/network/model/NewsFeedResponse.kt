package com.michael.network.model

data class NewsFeedResponse(
    val nextPage: String,
    val results: List<NewsFeedApiResult>,
    val status: String,
    val totalResults: Int
)