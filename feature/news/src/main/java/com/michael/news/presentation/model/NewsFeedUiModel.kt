package com.michael.news.presentation.model

internal data class NewsFeedUiModel(
    val articleId: String,
    val category: List<String>,
    val creator: List<String>,
    val description: String,
    val imageUrl: String,
    val keywords: List<String>,
    val pubDate: String,
    val title: String,
)
